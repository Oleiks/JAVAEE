package com.example.demo.song;

import com.example.demo.author.AuthorDto;
import com.example.demo.interceptor.SongModelGroup;
import com.example.demo.interceptor.SongValidator;
import com.example.demo.musicGenre.MusicGenreDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@ToString
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SongDto implements Serializable {

    private UUID id;
    @NotEmpty
    private String title;
    @SongValidator(length = 1.5, message = "Length is less than 1.5")
    private Double length;
    @NotNull
    private LocalDate premiereDate;
    private AuthorDto author;
    private MusicGenreDto musicGenre;
    private Long version;
    private LocalDateTime creationDateTime;
    private LocalDateTime updateDateTime;
}