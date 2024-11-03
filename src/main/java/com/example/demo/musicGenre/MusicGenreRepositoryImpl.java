package com.example.demo.musicGenre;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class MusicGenreRepositoryImpl implements MusicGenreRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<MusicGenre> getMusicGenres() {
        return em.createQuery("select mg from MusicGenre mg", MusicGenre.class).getResultList();
    }

    @Override
    public void saveMusicGenre(MusicGenre musicGenre) {
        em.persist(musicGenre);
    }

    @Override
    public Optional<MusicGenre> getMusicGenreByUUID(UUID uuid) {
        return Optional.ofNullable(em.find(MusicGenre.class, uuid));
    }

    @Override
    public void deleteMusicGenreById(UUID id) {
        em.remove(em.find(MusicGenre.class, id));
    }
}
