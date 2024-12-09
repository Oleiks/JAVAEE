package com.example.demo.song;

import com.example.demo.author.Author;
import com.example.demo.musicGenre.MusicGenre;
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
public class SongRepositoryImpl implements SongRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Song> getSongs() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Song> query = cb.createQuery(Song.class);
        Root<Song> root = query.from(Song.class);
        query.select(root);
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Song> getSongsByMusicGenre(MusicGenre musicGenre) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Song> query = cb.createQuery(Song.class);
        Root<Song> root = query.from(Song.class);
        query.where(cb.equal(root.get("musicGenre"), musicGenre));
        query.select(root);
        return em.createQuery(query).getResultList();
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

    @Override
    public void updateSong(Song Song) {
        em.merge(Song);
    }

    @Override
    public List<SongDto> findAllByAuthor(Author author) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Song> query = cb.createQuery(Song.class);
        Root<Song> root = query.from(Song.class);
        query.where(cb.equal(root.get("author"), author));
        query.select(root);
        return em.createQuery(query).getResultList().stream().map(SongMapper::toSongDto).toList();
    }

    @Override
    public List<SongDto> findAllByAuthorAndMusicGenre(Author author, MusicGenre musicGenre) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Song> query = cb.createQuery(Song.class);
        Root<Song> root = query.from(Song.class);
        query.where(cb.and(cb.equal(root.get("author"), author)), cb.equal(root.get("musicGenre"), musicGenre));
        query.select(root);
        return em.createQuery(query).getResultList().stream().map(SongMapper::toSongDto).toList();
    }

    @Override
    public List<Song> getSongsByMusicGenreAndFilter(MusicGenre musicGenre, SongDto filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Song> query = cb.createQuery(Song.class);
        Root<Song> root = query.from(Song.class);
        List<Predicate> predicates = songFilters(filter, root, cb);
        predicates.add(cb.equal(root.get("musicGenre"), musicGenre));
        query.where(cb.and(predicates.toArray(new Predicate[0])));
        query.select(root);
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<SongDto> findAllByAuthorAndMusicGenreAndFilter(Author author, MusicGenre musicGenre, SongDto filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Song> query = cb.createQuery(Song.class);
        Root<Song> root = query.from(Song.class);
        List<Predicate> predicates = songFilters(filter, root, cb);
        predicates.add(cb.equal(root.get("author"), author));
        predicates.add(cb.equal(root.get("musicGenre"), musicGenre));
        query.where(cb.and(predicates.toArray(new Predicate[0])));
        query.select(root);
        return em.createQuery(query).getResultList().stream().map(SongMapper::toSongDto).toList();
    }

    private List<Predicate> songFilters(SongDto filter, Root<Song> root, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if (filter.getLength() != null) {
            predicates.add(cb.equal(root.get("length"), filter.getLength()));
        }
        if (filter.getTitle() != null && !filter.getTitle().isEmpty()) {
            predicates.add(cb.equal(root.get("title"), filter.getTitle()));
        }
        if (filter.getPremiereDate() != null) {
            predicates.add(cb.equal(root.get("premiereDate"), filter.getPremiereDate()));
        }
        return predicates;
    }
}
