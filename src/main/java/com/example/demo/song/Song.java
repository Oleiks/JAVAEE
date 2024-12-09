package com.example.demo.song;

import com.example.demo.author.Author;
import com.example.demo.entity.VersionAndCreationDate;
import com.example.demo.musicGenre.MusicGenre;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@SuperBuilder
@Getter
@ToString
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Songs")
public class Song extends VersionAndCreationDate implements Serializable {
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
