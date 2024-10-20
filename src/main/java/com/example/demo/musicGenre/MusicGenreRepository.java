package com.example.demo.musicGenre;

import java.util.List;
import java.util.UUID;

public interface MusicGenreRepository {

    List<MusicGenre> getMusicGenres();

    void saveMusicGenre(MusicGenre musicGenre);

    MusicGenre getMusicGenreByUUID(UUID uuid);
}
