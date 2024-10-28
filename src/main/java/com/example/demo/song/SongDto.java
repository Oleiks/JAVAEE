package com.example.demo.song;

import com.example.demo.author.AuthorDto;
import com.example.demo.musicGenre.MusicGenreDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@ToString
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SongDto {

    private UUID id;
    private String title;
    private double length;
    private LocalDate premiereDate;
    private AuthorDto author;
    private MusicGenreDto musicGenre;
}