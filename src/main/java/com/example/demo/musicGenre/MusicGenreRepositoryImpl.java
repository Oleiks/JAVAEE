package com.example.demo.musicGenre;

import com.example.demo.dataStore.DataStore;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

@RequestScoped
public class MusicGenreRepositoryImpl implements MusicGenreRepository {

    private final DataStore dataStore;

    @Inject
    public MusicGenreRepositoryImpl(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public List<MusicGenre> getMusicGenres() {
        return dataStore.getMusicGenres();
    }

    @Override
    public void saveMusicGenre(MusicGenre musicGenre) {
        dataStore.saveMusicGenre(musicGenre);
    }

    @Override
    public MusicGenre getMusicGenreByUUID(UUID uuid) {
        return dataStore.getMusicGenreByUUID(uuid);
    }

    @Override
    public void deleteMusicGenreById(UUID id) {
        dataStore.deleteMusicGenreById(id);
    }
}
