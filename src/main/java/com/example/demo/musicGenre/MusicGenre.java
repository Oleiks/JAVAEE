package com.example.demo.musicGenre;

import com.example.demo.song.Song;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@ToString
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MusicGenres")
public class MusicGenre {
    @Id
    private UUID id;
    private String genre;
    private Integer yearOfOrigin;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "musicGenre", cascade = CascadeType.REMOVE)
    private List<Song> songs;
}
