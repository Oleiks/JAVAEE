package com.example.demo.song;

import com.example.demo.author.Author;
import com.example.demo.entity.VersionAndCreationDate;
import com.example.demo.interceptor.SongValidator;
import com.example.demo.musicGenre.MusicGenre;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
    @NotEmpty
    private String title;
    @SongValidator(length = 1.5, message = "Length is less than 1.5")
    private Double length;
    @NotNull
    private LocalDate premiereDate;
    @ManyToOne
    private Author author;
    @ManyToOne
    private MusicGenre musicGenre;
}
