package com.example.demo.song;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class SongRepositoryImpl implements SongRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Song> getSongs() {
        return em.createQuery("select s from Song s", Song.class).getResultList();
    }

    @Override
    public void saveSongs(Song Song) {
        em.persist(Song);
    }

    @Override
    public Optional<Song> getSongByUUID(UUID uuid) {
        return Optional.ofNullable(em.find(Song.class, uuid));
    }

    @Override
    public void deleteSongByUUID(UUID uuid) {
        em.remove(em.find(Song.class, uuid));
    }
}
