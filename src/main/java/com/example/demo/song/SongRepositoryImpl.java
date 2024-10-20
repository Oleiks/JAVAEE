package com.example.demo.song;

import com.example.demo.dataStore.DataStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class SongRepositoryImpl implements SongRepository {

    private final DataStore dataStore;

    @Inject
    public SongRepositoryImpl(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public List<Song> getSongs() {
        return dataStore.getSongs();
    }

    @Override
    public void saveSongs(Song Song) {
        dataStore.saveSongs(Song);
    }

    @Override
    public Song getSongByUUID(UUID uuid) {
        return dataStore.getSongByUUID(uuid);
    }
}
