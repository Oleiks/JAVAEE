package com.example.demo.musicGenre;

import com.example.demo.song.Song;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@ToString
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class MusicGenre {
    private UUID id;
    private String genre;
    private Integer yearOfOrigin;
    private List<Song> songs;
}
