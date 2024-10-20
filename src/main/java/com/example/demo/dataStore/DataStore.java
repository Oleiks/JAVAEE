package com.example.demo.dataStore;


import com.example.demo.author.Author;
import com.example.demo.exception.EntityExistsException;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.musicGenre.MusicGenre;
import com.example.demo.song.Song;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class DataStore {

    private final Set<Author> authors = new HashSet<>();

    private final Set<MusicGenre> musicGenres = new HashSet<>();

    private final Set<Song> songs = new HashSet<>();

    public List<Author> getAuthors() {
        return new ArrayList<>(authors);
    }

    public void saveAuthors(Author author) {
        Author entity = clone(author);
        if (entity.getSongs() == null) {
            entity.setSongs(new ArrayList<>());
            author.setSongs(new ArrayList<>());
        }
        if (authors.stream().anyMatch(a -> a.getId().equals(entity.getId()))) {
            throw new EntityExistsException("Author already exists");
        }
        entity.getSongs().forEach(song -> {
            if (songs.stream().noneMatch(song1 -> song1.getId().equals(song.getId()))) {
                throw new EntityExistsException("Song doesn't exist");
            } else {
                entity.setSongs(new ArrayList<>());
                song.setAuthor(entity);
            }
        });
        authors.add(author);
    }

    public Author getAuthorByUUID(UUID uuid) {
        return getAuthors().stream().filter(author -> author.getId().equals(uuid)).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Author with id " + uuid + " not found"));
    }

    public List<MusicGenre> getMusicGenres() {
        return new ArrayList<>(musicGenres);
    }

    public void saveMusicGenre(MusicGenre musicGenre) {
        MusicGenre entity = clone(musicGenre);
        if (entity.getSongs() == null) {
            entity.setSongs(new ArrayList<>());
            musicGenre.setSongs(new ArrayList<>());
        }
        if (musicGenres.stream().anyMatch(mg -> mg.getId().equals(entity.getId()))) {
            throw new EntityExistsException("Music Genre already exists");
        }
        entity.getSongs().forEach(song -> {
            if (songs.stream().noneMatch(song1 -> song1.getId().equals(song.getId()))) {
                throw new EntityExistsException("Song doesn't exist");
            } else {
                entity.setSongs(new ArrayList<>());
                song.setMusicGenre(entity);
            }
        });
        musicGenres.add(musicGenre);
    }

    public MusicGenre getMusicGenreByUUID(UUID uuid) {
        return getMusicGenres().stream().filter(musicGenre -> musicGenre.getId().equals(uuid)).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Music genre with id " + uuid + " not found"));
    }

    public List<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    public void saveSongs(Song song) throws EntityNotFoundException {
        Song entity = clone(song);
//        entity.setMusicGenre(null);
//        entity.setAuthor(null);
        if (songs.stream().anyMatch(song1 -> song1.getId().equals(song.getId()))) {
            throw new EntityExistsException("Song already exists");
        }
        if (song.getMusicGenre() != null) {
            musicGenres.stream().filter(musicGenre -> musicGenre.getId().equals(song.getMusicGenre().getId())).findFirst()
                    .orElseThrow(() -> new EntityExistsException("Music genre doesn't exist")).getSongs().add(entity);
        }
        if (song.getAuthor() != null) {
            authors.stream().filter(author -> author.getId().equals(song.getAuthor().getId())).findFirst()
                    .orElseThrow(() -> new EntityExistsException("Author doesn't exist")).getSongs().add(entity);
        }
        songs.add(entity);
    }

    public Song getSongByUUID(UUID uuid) {
        return getSongs().stream().filter(musicGenre -> musicGenre.getId().equals(uuid)).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Song with id " + uuid + " not found"));
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    private <T extends Serializable> T clone(T object) {
        try (ByteArrayInputStream is = new ByteArrayInputStream(writeObject(object).toByteArray());
             ObjectInputStream ois = new ObjectInputStream(is)) {
            return (T) ois.readObject();
        }
    }

    private <T extends Serializable> ByteArrayOutputStream writeObject(T object) throws IOException {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(object);
            return os;
        }
    }
}
