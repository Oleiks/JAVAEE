package com.example.demo.song;

public class SongMapper {
    public static SongDto toSongDto(Song song) {
        return SongDto.builder()
                .title(song.getTitle())
                .length(song.getLength())
                .premiereDate(song.getPremiereDate())
                .id(song.getId())
                .build();
    }
}
