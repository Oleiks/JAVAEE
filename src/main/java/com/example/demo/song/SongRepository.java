package com.example.demo.song;

import com.example.demo.author.Author;

import com.example.demo.musicGenre.MusicGenre;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SongRepository {
    List<Song> getSongs();

    void saveSongs(Song Song);

    Optional<Song> getSongByUUID(UUID uuid);

    void deleteSongByUUID(UUID uuid);

    void updateSong(Song Song);

    List<SongDto> findAllByAuthor(Author author);

    List<Song> getSongsByMusicGenre(MusicGenre musicGenre);

    List<SongDto> findAllByAuthorAndMusicGenre(Author author, MusicGenre musicGenre);
}
