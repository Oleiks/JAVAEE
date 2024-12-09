package com.example.demo.musicGenre;

import lombok.*;

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
    private Integer yearOfOrigin;
}