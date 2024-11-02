package com.example.demo.dataStore;


import com.example.demo.author.Author;
import com.example.demo.exception.EntityExistsException;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.musicGenre.MusicGenre;
import com.example.demo.song.Song;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.*;
import java.util.*;

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
        if (authors.stream().anyMatch(a -> a.getId().equals(entity.getId()))) {
            throw new EntityExistsException("Author already exists");
        }
        if (entity.getSongs() != null) {
            entity.getSongs().forEach(song -> {
                if (songs.stream().noneMatch(song1 -> song1.getId().equals(song.getId()))) {
                    throw new EntityExistsException("Song doesn't exist");
                } else {
                    song.setAuthor(entity);
                }
            });
        }
        authors.add(entity);
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
        if (musicGenres.stream().anyMatch(mg -> mg.getId().equals(musicGenre.getId()))) {
            throw new EntityExistsException("Music Genre already exists");
        }
        if (entity.getSongs() != null) {
            entity.getSongs().forEach(song -> {
                if (songs.stream().noneMatch(song1 -> song1.getId().equals(song.getId()))) {
                    throw new EntityExistsException("Song doesn't exist");
                } else {
                    song.setMusicGenre(entity);
                }
            });
        }
        musicGenres.add(entity);
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
        if (songs.stream().anyMatch(song1 -> song1.getId().equals(song.getId()))) {
            throw new EntityExistsException("Song already exists");
        }
        if (song.getMusicGenre() != null) {
            MusicGenre musicGenre = musicGenres.stream().filter(musicGenre1 -> musicGenre1.getId().equals(song.getMusicGenre().getId())).findFirst()
                    .orElseThrow(() -> new EntityExistsException("Music genre doesn't exist"));
            if (musicGenre.getSongs() != null) {
                musicGenre.getSongs().add(entity);
            } else {
                musicGenre.setSongs(new ArrayList<>());
                musicGenre.getSongs().add(entity);
            }
        }
        if (song.getAuthor() != null) {
            Author author = authors.stream().filter(author1 -> author1.getId().equals(song.getAuthor().getId())).findFirst()
                    .orElseThrow(() -> new EntityExistsException("Author doesn't exist"));
            if (author.getSongs() != null) {
                author.getSongs().add(entity);
            } else {
                author.setSongs(new ArrayList<>());
                author.getSongs().add(entity);
            }
        }
        songs.add(entity);
    }

    public Song getSongByUUID(UUID uuid) {
        return getSongs().stream().filter(song -> song.getId().equals(uuid)).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Song with id " + uuid + " not found"));
    }

    public void deleteSongByUUID(UUID uuid) {
        Song song = getSongByUUID(uuid);
        UUID musicGenreId = song.getMusicGenre().getId();
        MusicGenre musicGenre = musicGenres.stream().filter(musicGenre1 -> musicGenre1.getId().equals(musicGenreId)).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Song with id " + musicGenreId + " not found"));
        musicGenre.getSongs().removeIf(song1 -> song1.getId().equals(uuid));
        songs.removeIf(s -> s.getId().equals(uuid));
    }

    public void deleteMusicGenreById(UUID musicGenreId) {
        MusicGenre musicGenre = getMusicGenreByUUID(musicGenreId);
        musicGenre.getSongs().forEach(s -> {
            Song song = getSongByUUID(s.getId());
            Author author = getAuthorByUUID(s.getAuthor().getId());
            author.getSongs().remove(song);
            songs.remove(song);
        });
        musicGenres.removeIf(musicG -> musicG.getId().equals(musicGenreId));
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
