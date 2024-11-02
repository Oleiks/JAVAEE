package com.example.demo.musicGenre;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequestScoped
@NoArgsConstructor(force = true)
public class MusicGenreService {

    private final MusicGenreRepository musicGenreRepository;

    @Inject
    public MusicGenreService(MusicGenreRepository musicGenreRepository) {
        this.musicGenreRepository = musicGenreRepository;
    }

    public List<MusicGenreDto> findAll() {
        return musicGenreRepository.getMusicGenres().stream().map(MusicGenreMapper::toMusicGenreDto).toList();
    }

    public MusicGenreDto findById(UUID id) {
        return MusicGenreMapper.toMusicGenreDto(find(id));
    }

    public void create(MusicGenre musicGenre) {
        musicGenreRepository.saveMusicGenre(musicGenre);
    }

    public void updateMusicGenre(UUID uuid, PatchMusicGenreRequest request) {
        MusicGenre musicGenre = find(uuid);
        if (request.getGenre() != null) {
            musicGenre.setGenre(request.getGenre());
        }
        if (request.getYearOfOrigin() != null) {
            musicGenre.setYearOfOrigin(request.getYearOfOrigin());
        }
    }

    public MusicGenre find(UUID id) {
        return musicGenreRepository.getMusicGenreByUUID(id);
    }

    public void delete(UUID id) {
        musicGenreRepository.deleteMusicGenreById(id);
    }
}
