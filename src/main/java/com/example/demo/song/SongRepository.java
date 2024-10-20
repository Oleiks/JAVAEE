package com.example.demo.song;

import java.util.List;
import java.util.UUID;

public interface SongRepository {
    List<Song> getSongs();

    void saveSongs(Song Song);

    Song getSongByUUID(UUID uuid);
}
