package com.example.demo.song;

import com.example.demo.author.AuthorRepository;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.musicGenre.MusicGenreRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class SongService {

    private final SongRepository songRepository;

    private final MusicGenreRepository musicGenreRepository;

    private final AuthorRepository authorRepository;

    @Inject
    public SongService(SongRepository songRepository,
                       MusicGenreRepository musicGenreRepository,
                       AuthorRepository authorRepository) {
        this.songRepository = songRepository;
        this.musicGenreRepository = musicGenreRepository;
        this.authorRepository = authorRepository;
    }

    public List<SongDto> findAll() {
        return songRepository.getSongs().stream().map(SongMapper::toSongDto).toList();
    }

    public List<SongDto> findAllByMusicGenreId(UUID uuid) {
        return songRepository.getSongs().stream().filter(song -> song.getMusicGenre().getId().equals(uuid)).map(SongMapper::toSongDto).toList();
    }

    public SongDto findByMusicGenreId(UUID musicGenreUuid, UUID songUuid) {
        return songRepository.getSongs().stream().filter(song -> song.getId().equals(songUuid) && song.getMusicGenre().getId().equals(musicGenreUuid)).findAny().map(SongMapper::toSongDto)
                .orElseThrow(() -> new EntityNotFoundException("Song with uuid " + songUuid + " in music genre with uuid " + musicGenreUuid + " not found"));
    }

    public SongDto findById(UUID id) {
        return SongMapper.toSongDto(find(id));
    }

    @Transactional
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

    @Transactional
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

    @Transactional
    public void updateSong(UUID uuid, SongDto songDto) {
        Song song = find(uuid);
        editSong(song, songDto.getTitle(), songDto.getPremiereDate(), songDto.getLength());
    }

    @Transactional
    public void updateSong(UUID songId, UUID musicGenreId, PatchSongRequest request) {
        Song song = find(songId);
        if (!song.getMusicGenre().getId().equals(musicGenreId)) {
            throw new EntityNotFoundException("Song with id " + songId + "not found in music genre with id " + musicGenreId);
        }
        editSong(song, request.getTitle(), request.getPremiereDate(), request.getLength());
//        musicGenreService.findById(musicGenreId).getSongs().stream()
//                .filter(s->s.getId().equals(songId)).findFirst().map(s->editSong(s, request.getTitle(), request.getPremiereDate(), request.getLength()))
//                .orElseThrow(()->new EntityNotFoundException("Song with id " + songId + "not found in music genre with id " + musicGenreId));
    }

    @Transactional
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
    }

    @Transactional
    private SongDto editSong(SongDto song, String title, LocalDate premiereDate, Double length) {
        if (title != null) {
            song.setTitle(title);
        }
        if (premiereDate != null) {
            song.setLength(length);
        }
        if (premiereDate != null) {
            song.setPremiereDate(premiereDate);
        }
        return song;
    }

    private Song find(UUID id) {
        return songRepository.getSongByUUID(id)
                .orElseThrow(() -> new EntityNotFoundException("Song with uuid " + id + " not found"));
    }

    @Transactional
    public void delete(UUID id) {
        songRepository.deleteSongByUUID(id);
    }

    @Transactional
    public void delete(UUID musicGenreUuid, UUID songUuid) throws EntityNotFoundException {
        Song song = find(songUuid);
        if (song.getMusicGenre().getId().equals(musicGenreUuid)) {
            songRepository.deleteSongByUUID(songUuid);
        } else {
            throw new EntityNotFoundException("Avatar not found");
        }
    }
}
