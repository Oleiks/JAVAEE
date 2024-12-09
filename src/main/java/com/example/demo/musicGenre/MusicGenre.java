package com.example.demo.musicGenre;

import com.example.demo.song.Song;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
