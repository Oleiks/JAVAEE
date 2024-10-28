package com.example.demo.musicGenre;

import com.example.demo.song.SongDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Builder
@ToString
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MusicGenreDto {
    private UUID id;
    private String genre;
    private int yearOfOrigin;
    private List<SongDto> songs;
}