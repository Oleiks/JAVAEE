package com.example.demo.song;

import com.example.demo.author.Author;
import com.example.demo.musicGenre.MusicGenre;
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
@Getter
@ToString
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Song {
    private UUID id;
    private String title;
    private Double length;
    private LocalDate premiereDate;
    private Author author;
    private MusicGenre musicGenre;
}
