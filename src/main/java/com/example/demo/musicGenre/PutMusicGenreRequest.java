package com.example.demo.musicGenre;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutMusicGenreRequest {
    private String genre;
    private Integer yearOfOrigin;
}
