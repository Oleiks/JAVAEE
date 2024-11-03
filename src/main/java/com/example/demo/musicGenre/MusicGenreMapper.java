package com.example.demo.musicGenre;

import java.util.UUID;

public class MusicGenreMapper {
    public static MusicGenreDto toMusicGenreDto(MusicGenre musicGenre) {
        return MusicGenreDto.builder()
                .genre(musicGenre.getGenre())
                .id(musicGenre.getId())
                .yearOfOrigin(musicGenre.getYearOfOrigin())
                .build();
    }

    public static MusicGenre toMusicGenre(PutMusicGenreRequest request) {
        return MusicGenre.builder()
                .genre(request.getGenre())
                .id(UUID.randomUUID())
                .yearOfOrigin(request.getYearOfOrigin())
                .build();
    }
}
