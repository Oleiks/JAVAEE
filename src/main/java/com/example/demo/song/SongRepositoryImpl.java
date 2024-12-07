package com.example.demo.song;

import com.example.demo.author.Author;
import com.example.demo.musicGenre.MusicGenre;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

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
        return em.createQuery("select s from Song s", Song.class).getResultList();
    }

    @Override
    public List<Song> getSongsByMusicGenre(MusicGenre musicGenre) {
        return em.createQuery("select s from Song s where s.musicGenre=:musicGenre", Song.class)
                .setParameter("musicGenre", musicGenre)
                .getResultList();
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
        return em.createQuery("select s from Song s where s.author = :author", Song.class)
                .setParameter("author", author)
                .getResultList().stream().map(SongMapper::toSongDto).toList();
    }

    @Override
    public List<SongDto> findAllByAuthorAndMusicGenre(Author author, MusicGenre musicGenre) {
        return em.createQuery("select s from Song s where s.author = :author and s.musicGenre = :musicGenre", Song.class)
                .setParameter("author", author)
                .setParameter("musicGenre", musicGenre)
                .getResultList().stream().map(SongMapper::toSongDto).toList();
    }

}
