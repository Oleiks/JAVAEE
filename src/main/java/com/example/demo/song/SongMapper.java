package com.example.demo.song;

import com.example.demo.musicGenre.MusicGenre;

import java.util.UUID;

public class SongMapper {
    public static SongDto toSongDto(Song song) {
        return SongDto.builder()
                .title(song.getTitle())
                .length(song.getLength())
                .premiereDate(song.getPremiereDate())
                .id(song.getId())
                .build();
    }

    public static Song toSong(PutSongRequest request, MusicGenre musicGenre) {
        return Song.builder()
                .title(request.getTitle())
                .length(request.getLength())
                .premiereDate(request.getPremiereDate())
                .id(UUID.randomUUID())
                .musicGenre(musicGenre)
                .build();
    }
}
