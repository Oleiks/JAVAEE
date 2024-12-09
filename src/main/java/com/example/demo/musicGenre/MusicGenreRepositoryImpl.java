package com.example.demo.musicGenre;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
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
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<MusicGenre> query = cb.createQuery(MusicGenre.class);
        Root<MusicGenre> root = query.from(MusicGenre.class);
        query.select(root);
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<MusicGenre> getMusicGenresByFilter(MusicGenreDto musicGenreDto) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<MusicGenre> query = cb.createQuery(MusicGenre.class);
        Root<MusicGenre> root = query.from(MusicGenre.class);
        List<Predicate> predicates = new ArrayList<>();
        if(musicGenreDto.getGenre()!=null){
            predicates.add(cb.equal(root.get("musicGenre"), musicGenreDto.getGenre()));
        }
        if(musicGenreDto.getYearOfOrigin()!=null){
            predicates.add(cb.equal(root.get("musicGenre"), musicGenreDto.getYearOfOrigin()));
        }
        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[0])));
        }
        query.select(root);
        return em.createQuery(query).getResultList();
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

    @Override
    public void updateMusicGenre(MusicGenre musicGenre) {
        em.merge(musicGenre);
    }
}
