package com.example.demo.song;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PatchSongRequest {
    private String title;
    private Double length;
    private LocalDate premiereDate;
}