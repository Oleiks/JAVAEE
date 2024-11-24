package com.example.demo.song;

import com.example.demo.author.Author;
import com.example.demo.author.AuthorRepository;
import com.example.demo.author.UserRoles;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.musicGenre.MusicGenreRepository;
import jakarta.annotation.security.PermitAll;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
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

    @PermitAll
    public List<SongDto> findAll() {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return songRepository.getSongs().stream().map(SongMapper::toSongDto).toList();
        }
        Author user = authorRepository.getAuthorByName(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return songRepository.findAllByAuthor(user);
    }

    public List<SongDto> findAllByMusicGenreId(UUID uuid) {
        return songRepository.getSongsByMusicGenre(musicGenreRepository.getMusicGenreByUUID(uuid).orElseThrow(() -> new EntityNotFoundException("MusicGenre doesn't exist."))).stream().map(SongMapper::toSongDto).toList();
    }

    public SongDto findByMusicGenreId(UUID musicGenreUuid, UUID songUuid) {
        return songRepository.getSongs().stream().filter(song -> song.getId().equals(songUuid) && song.getMusicGenre().getId().equals(musicGenreUuid)).findAny().map(SongMapper::toSongDto)
                .orElseThrow(() -> new EntityNotFoundException("Song with uuid " + songUuid + " in music genre with uuid " + musicGenreUuid + " not found"));
    }

    public SongDto findById(UUID id) {
        return SongMapper.toSongDto(find(id));
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

    public void updateSong(UUID uuid, SongDto songDto) {
        Song song = find(uuid);
        editSong(song, songDto.getTitle(), songDto.getPremiereDate(), songDto.getLength());
    }

    public void updateSong(UUID songId, UUID musicGenreId, PatchSongRequest request) {
        Song song = find(songId);
        if (!song.getMusicGenre().getId().equals(musicGenreId)) {
            throw new EntityNotFoundException("Song with id " + songId + "not found in music genre with id " + musicGenreId);
        }
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

    public void delete(UUID id) {
        songRepository.deleteSongByUUID(id);
    }

    public void delete(UUID musicGenreUuid, UUID songUuid) throws EntityNotFoundException {
        Song song = find(songUuid);
        if (song.getMusicGenre().getId().equals(musicGenreUuid)) {
            songRepository.deleteSongByUUID(songUuid);
        } else {
            throw new EntityNotFoundException("Avatar not found");
        }
    }
}
