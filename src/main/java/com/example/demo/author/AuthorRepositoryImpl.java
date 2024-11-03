package com.example.demo.author;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class AuthorRepositoryImpl implements AuthorRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }


    @Override
    public List<Author> getAuthors() {
        return em.createQuery("select a from Author a", Author.class).getResultList();
    }

    @Override
    public void saveAuthors(Author author) {
        em.persist(author);
    }

    @Override
    public Optional<Author> getAuthorByUUID(UUID uuid) {
        return Optional.ofNullable(em.find(Author.class, uuid));
    }
}
