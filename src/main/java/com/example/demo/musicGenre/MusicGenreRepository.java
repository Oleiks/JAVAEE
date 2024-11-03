package com.example.demo.musicGenre;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MusicGenreRepository {

    List<MusicGenre> getMusicGenres();

    void saveMusicGenre(MusicGenre musicGenre);

    Optional<MusicGenre> getMusicGenreByUUID(UUID uuid);

    void deleteMusicGenreById(UUID id);
}
