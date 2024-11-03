package com.example.demo.song;

import com.example.demo.author.Author;
import com.example.demo.musicGenre.MusicGenre;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Getter
@ToString
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Songs")
public class Song {
    @Id
    private UUID id;
    private String title;
    private Double length;
    private LocalDate premiereDate;
    @ManyToOne
    private Author author;
    @ManyToOne
    private MusicGenre musicGenre;
}
