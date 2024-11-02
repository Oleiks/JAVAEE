package com.example.demo.configuration.observer;

import com.example.demo.author.Author;
import com.example.demo.author.AuthorService;
import com.example.demo.author.Type;
import com.example.demo.musicGenre.MusicGenre;
import com.example.demo.musicGenre.MusicGenreService;
import com.example.demo.song.Song;
import com.example.demo.song.SongService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.util.UUID;

@ApplicationScoped
public class InitializedData {

    private final AuthorService authorService;
    private final MusicGenreService musicGenreService;
    private final SongService songService;

    private final RequestContextController requestContextController;

    @Inject
    public InitializedData(AuthorService authorService,
                           RequestContextController requestContextController,
                           MusicGenreService musicGenreService,
                           SongService songService) {
        this.authorService = authorService;
        this.musicGenreService = musicGenreService;
        this.songService = songService;
        this.requestContextController = requestContextController;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    @SneakyThrows
    private void init() {
        requestContextController.activate();
        Author author = Author.builder()
                .id(UUID.fromString("bc72126e-9a5e-4304-a28a-df340312760f"))
                .name("Kanye West")
                .debutYear(1996)
                .type(Type.SOLO)
                .build();
        Author author2 = Author.builder()
                .name("Linkin Park")
                .id(UUID.fromString("7e7ae796-759e-4075-bb41-b6b40cedfc18"))
                .debutYear(1996)
                .type(Type.BAND)
                .build();
        Author author3 = Author.builder()
                .id(UUID.fromString("a25b09aa-4c2b-4159-ab07-c1ca93108a4a"))
                .name("AC/DC")
                .debutYear(1973)
                .type(Type.BAND)
                .build();
        Author author4 = Author.builder()
                .name("Kendrick Lamar")
                .id(UUID.fromString("de8603ba-b47f-4ee2-9b70-12ba721649a9"))
                .debutYear(2003)
                .type(Type.SOLO)
                .build();

        authorService.create(author);
        authorService.create(author2);
        authorService.create(author3);
        authorService.create(author4);

        MusicGenre musicGenre = MusicGenre.builder()
                .genre("Rap")
                .yearOfOrigin(1973)
                .id(UUID.fromString("16727996-09dc-4220-aacd-051069e17ba6"))
                .build();
        MusicGenre musicGenre1 = MusicGenre.builder()
                .genre("Rock")
                .yearOfOrigin(1950)
                .id(UUID.fromString("7154908a-0aba-4967-814b-86740407acfb"))
                .build();

        musicGenreService.create(musicGenre);
        musicGenreService.create(musicGenre1);

        Song song = Song.builder()
                .id(UUID.randomUUID())
                .musicGenre(musicGenre1)
                .author(author3)
                .title("Back In Black")
                .length(4.14)
                .premiereDate(LocalDate.of(1980, 7, 25))
                .build();
        Song song1 = Song.builder()
                .id(UUID.randomUUID())
                .musicGenre(musicGenre)
                .author(author)
                .title("Famous")
                .length(3.17)
                .premiereDate(LocalDate.of(2016, 2, 14))
                .build();
        Song song2 = Song.builder()
                .id(UUID.randomUUID())
                .musicGenre(musicGenre)
                .author(author4)
                .title("DNA")
                .length(3.06)
                .premiereDate(LocalDate.of(2017, 4, 14))
                .build();
        Song song3 = Song.builder()
                .id(UUID.randomUUID())
                .musicGenre(musicGenre1)
                .author(author2)
                .title("Heavy Is the Crown")
                .length(2.48)
                .premiereDate(LocalDate.of(2024, 9, 24))
                .build();

        songService.create(song);
        songService.create(song1);
        songService.create(song2);
        songService.create(song3);
        requestContextController.deactivate();
    }
}
