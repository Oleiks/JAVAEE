package com.example.demo.musicGenre;

public class MusicGenreMapper {
    public static MusicGenreDto toMusicGenreDto(MusicGenre musicGenre) {
        return MusicGenreDto.builder()
                .genre(musicGenre.getGenre())
                .songs(musicGenre.getSongs())
                .id(musicGenre.getId())
                .yearOfOrigin(musicGenre.getYearOfOrigin())
                .build();
    }
}
