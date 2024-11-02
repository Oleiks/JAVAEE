package com.example.demo.musicGenre;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PatchMusicGenreRequest {
    private String genre;
    private Integer yearOfOrigin;
}