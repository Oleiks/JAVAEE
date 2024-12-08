package com.example.demo.song;

import com.example.demo.author.Author;
import com.example.demo.author.AuthorRepository;
import com.example.demo.author.UserRoles;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.musicGenre.MusicGenre;
import com.example.demo.musicGenre.MusicGenreRepository;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.ws.rs.NotAuthorizedException;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class SongService {

    private final SongRepository songRepository;

    private final MusicGenreRepository musicGenreRepository;

    private final AuthorRepository authorRepository;

    private final SecurityContext securityContext;

    @Inject
    public SongService(SongRepository songRepository,
                       MusicGenreRepository musicGenreRepository,
                       AuthorRepository authorRepository,
                       @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext) {
        this.songRepository = songRepository;
        this.musicGenreRepository = musicGenreRepository;
        this.authorRepository = authorRepository;
        this.securityContext = securityContext;
    }

    @RolesAllowed({UserRoles.ADMIN, UserRoles.USER})
    public List<SongDto> findAll() {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return songRepository.getSongs().stream().map(SongMapper::toSongDto).toList();
        }
        Author user = authorRepository.getAuthorByName(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return songRepository.findAllByAuthor(user);
    }

    @RolesAllowed({UserRoles.ADMIN, UserRoles.USER})
    public List<SongDto> findAllByMusicGenreId(UUID uuid) {
        MusicGenre musicGenre = musicGenreRepository.getMusicGenreByUUID(uuid).orElseThrow(() -> new EntityNotFoundException("MusicGenre doesn't exist."));
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return songRepository.getSongsByMusicGenre(musicGenre).stream().map(SongMapper::toSongDto).toList();
        }
        Author user = authorRepository.getAuthorByName(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return songRepository.findAllByAuthorAndMusicGenre(user, musicGenre);
    }

    public SongDto findByMusicGenreId(UUID musicGenreUuid, UUID songUuid) {
        return songRepository.getSongs().stream().filter(song -> song.getId().equals(songUuid) && song.getMusicGenre().getId().equals(musicGenreUuid)).findAny().map(SongMapper::toSongDto)
                .orElseThrow(() -> new EntityNotFoundException("Song with uuid " + songUuid + " in music genre with uuid " + musicGenreUuid + " not found"));
    }

    @RolesAllowed({UserRoles.ADMIN, UserRoles.USER})
    public SongDto findById(UUID id) {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return SongMapper.toSongDto(find(id));
        } else {
            Song song = find(id);
            return song.getAuthor().getName().equals(securityContext.getCallerPrincipal().getName())
                    ? SongMapper.toSongDto(song)
                    : SongMapper.toSongDto(null);
        }
    }

    @RolesAllowed({UserRoles.ADMIN, UserRoles.USER})
    public SongDto findByIdC(UUID musicGenreUuid, UUID songUuid) {
        if (securityContext.isCallerInRole(UserRoles.USER)) {
            Song song = find(songUuid);
            if (song.getMusicGenre().getId().equals(musicGenreUuid)) {
                throw new EntityNotFoundException("Song not found");
            }
            if (song.getAuthor().getName().equals(securityContext.getCallerPrincipal().getName())) {
                throw new EntityNotFoundException("Song not found");
            }
            return SongMapper.toSongDto(song);
        }
        return SongMapper.toSongDto(find(songUuid));
    }

    public void create(Song song) {
        if (songRepository.getSongByUUID(song.getId()).isPresent()) {
            throw new IllegalArgumentException("Song with uuid " + song.getId() + " already exists");
        }
        if (musicGenreRepository.getMusicGenreByUUID(song.getMusicGenre().getId()).isEmpty()) {
            throw new IllegalArgumentException("MusicGenre with uuid " + song.getId() + " doesn't exists");
        }
        songRepository.saveSongs(song);
        musicGenreRepository.getMusicGenreByUUID(song.getMusicGenre().getId())
                .ifPresent(mg -> mg.getSongs().add(song));
    }

    @RolesAllowed({UserRoles.ADMIN, UserRoles.USER})
    public void createSongs(Song song) {
        Author author = authorRepository.getAuthorByName(securityContext.getCallerPrincipal().getName()).orElseThrow(() -> new EntityNotFoundException("Author not found"));
        song.setAuthor(author);
        if (songRepository.getSongByUUID(song.getId()).isPresent()) {
            throw new IllegalArgumentException("Song with uuid " + song.getId() + " already exists");
        }
        if (musicGenreRepository.getMusicGenreByUUID(song.getMusicGenre().getId()).isEmpty()) {
            throw new IllegalArgumentException("MusicGenre with uuid " + song.getId() + " doesn't exists");
        }
        songRepository.saveSongs(song);
        musicGenreRepository.getMusicGenreByUUID(song.getMusicGenre().getId())
                .ifPresent(mg -> mg.getSongs().add(song));
    }

    public void createSong(SongCommand songCommand) {
        Song Song = new Song();
        Song.setId(UUID.randomUUID());
        if (songCommand.getTitle() != null) {
            Song.setTitle(songCommand.getTitle());
        }
        if (songCommand.getLength() != null) {
            Song.setLength(songCommand.getLength());
        }
        if (songCommand.getPremiereDate() != null) {
            Song.setPremiereDate(songCommand.getPremiereDate());
        }
        if (songCommand.getMusicGenre() != null) {
            Song.setMusicGenre(musicGenreRepository.getMusicGenreByUUID(songCommand.getMusicGenre()).orElseThrow(() -> new EntityNotFoundException("Music genre not found")));
        }
        songRepository.saveSongs(Song);
    }

    @RolesAllowed({UserRoles.ADMIN, UserRoles.USER})
    public void updateSong(UUID uuid, SongDto songDto) {
        Song song = find(uuid);
        Author author = authorRepository.getAuthorByName(securityContext.getCallerPrincipal().getName()).orElseThrow(() -> new EntityNotFoundException("Author not found"));
        if ((securityContext.isCallerInRole(UserRoles.USER) && song.getAuthor().getName().equals(securityContext.getCallerPrincipal().getName())) || (securityContext.isCallerInRole(UserRoles.ADMIN))) {
            editSong(song, songDto.getTitle(), songDto.getPremiereDate(), songDto.getLength());
        }
    }

    @RolesAllowed({UserRoles.ADMIN, UserRoles.USER})
    public void updateSong(UUID songId, UUID musicGenreId, PatchSongRequest request) {
        Song song = find(songId);
        if (securityContext.isCallerInRole(UserRoles.USER) && song.getAuthor().getName().equals(securityContext.getCallerPrincipal().getName())) {
            throw new NotAuthorizedException("Song doesn't belong to author");
        }
        if (!song.getMusicGenre().getId().equals(musicGenreId)) {
            throw new EntityNotFoundException("Song with id " + songId + "not found in music genre with id " + musicGenreId);
        }
        Author author = authorRepository.getAuthorByName(securityContext.getCallerPrincipal().getName()).orElseThrow(() -> new EntityNotFoundException("Author not found"));
        editSong(song, request.getTitle(), request.getPremiereDate(), request.getLength());
    }

    private void editSong(Song song, String title, LocalDate premiereDate, Double length) {
        if (title != null) {
            song.setTitle(title);
        }
        if (premiereDate != null) {
            song.setLength(length);
        }
        if (premiereDate != null) {
            song.setPremiereDate(premiereDate);
        }
        songRepository.updateSong(song);
    }

    private Song find(UUID id) {
        return songRepository.getSongByUUID(id)
                .orElseThrow(() -> new EntityNotFoundException("Song with uuid " + id + " not found"));
    }

    @RolesAllowed({UserRoles.ADMIN, UserRoles.USER})
    public void delete(UUID id) {
        Author author = authorRepository.getAuthorByName(securityContext.getCallerPrincipal().getName()).orElseThrow(() -> new EntityNotFoundException("Author not found"));
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            songRepository.deleteSongByUUID(id);
        } else {
            Song song = songRepository.getSongByUUID(id).orElseThrow(() -> new EntityNotFoundException("Author not found"));
            if (!song.getAuthor().getName().equals(author.getName())) {
                throw new NotAuthorizedException("Song doesn't belong to author");
            }
            songRepository.deleteSongByUUID(id);
        }
    }

    @RolesAllowed({UserRoles.ADMIN, UserRoles.USER})
    public void delete(UUID musicGenreUuid, UUID songUuid) throws EntityNotFoundException {
        Author author = authorRepository.getAuthorByName(securityContext.getCallerPrincipal().getName()).orElseThrow(() -> new EntityNotFoundException("Author not found"));
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            Song song = find(songUuid);
            if (song.getMusicGenre().getId().equals(musicGenreUuid)) {
                songRepository.deleteSongByUUID(songUuid);
            } else {
                throw new EntityNotFoundException("Songs doesn't belong to this music genre");
            }
        } else if (securityContext.isCallerInRole(UserRoles.USER)) {
            Song song = find(songUuid);
            if (!song.getAuthor().getId().equals(securityContext.getCallerPrincipal().getName())) {
                throw new NotAuthorizedException("Songs doesn't belong to author");
            }
            if (song.getMusicGenre().getId().equals(musicGenreUuid)) {
                songRepository.deleteSongByUUID(songUuid);
            } else {
                throw new EntityNotFoundException("Songs doesn't belong to this music genre");
            }
        }
    }
}
