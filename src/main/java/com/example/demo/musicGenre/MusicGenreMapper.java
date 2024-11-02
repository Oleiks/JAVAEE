package com.example.demo.musicGenre;

import com.example.demo.song.SongMapper;

import java.util.UUID;

public class MusicGenreMapper {
    public static MusicGenreDto toMusicGenreDto(MusicGenre musicGenre) {
        return MusicGenreDto.builder()
                .genre(musicGenre.getGenre())
                .songs(musicGenre.getSongs()!=null?musicGenre.getSongs().stream().map(SongMapper::toSongDto).toList():null)
                .id(musicGenre.getId())
                .yearOfOrigin(musicGenre.getYearOfOrigin())
                .build();
    }

    public static MusicGenre toMusicGenre(PutMusicGenreRequest request){
        return MusicGenre.builder()
                .genre(request.getGenre())
                .id(UUID.randomUUID())
                .yearOfOrigin(request.getYearOfOrigin())
                .build();
    }
}
