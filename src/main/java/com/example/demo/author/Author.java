package com.example.demo.author;

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
@Table(name = "Authors")
public class Author {
    @Id
    private UUID id;
    private String name;
    private Integer debutYear;
    private Type type;
}
