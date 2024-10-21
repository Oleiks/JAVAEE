package com.example.demo.musicGenre;

import com.example.demo.song.SongMapper;

public class MusicGenreMapper {
    public static MusicGenreDto toMusicGenreDto(MusicGenre musicGenre) {
        return MusicGenreDto.builder()
                .genre(musicGenre.getGenre())
                .songs(musicGenre.getSongs().stream().map(SongMapper::toSongDto).toList())
                .id(musicGenre.getId())
                .yearOfOrigin(musicGenre.getYearOfOrigin())
                .build();
    }
}
