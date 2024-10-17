package com.example.demo.author;

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
public class Author {
    private UUID id;
    private String name;
    private Integer debutYear;
    private Type type;
    private List<Song> songs;
}
