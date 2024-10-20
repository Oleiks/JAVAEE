package com.example.demo.musicGenre;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class MusicGenreService {

    private final MusicGenreRepository musicGenreRepository;

    @Inject
    public MusicGenreService(MusicGenreRepository musicGenreRepository) {
        this.musicGenreRepository = musicGenreRepository;
    }

    public synchronized List<MusicGenreDto> findAll() {
        return musicGenreRepository.getMusicGenres().stream().map(MusicGenreMapper::toMusicGenreDto).toList();
    }

    public synchronized MusicGenreDto findById(UUID id) {
        return MusicGenreMapper.toMusicGenreDto(find(id));
    }

    public synchronized void create(MusicGenre musicGenre) {
        musicGenre.setId(UUID.randomUUID());
        musicGenreRepository.saveMusicGenre(musicGenre);
    }

    public synchronized void updateMusicGenre(UUID uuid, MusicGenreCommand musicGenreCommand) {
        MusicGenre musicGenre = find(uuid);
        if (musicGenre != null) {
            if (musicGenreCommand.getGenre() != null) {
                musicGenre.setGenre(musicGenreCommand.getGenre());
            }
            if (musicGenreCommand.getYearOfOrigin() != null) {
                musicGenre.setYearOfOrigin(musicGenreCommand.getYearOfOrigin());
            }
        }
    }

    private MusicGenre find(UUID id) {
        return musicGenreRepository.getMusicGenreByUUID(id);
    }
}
