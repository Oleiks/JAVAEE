package com.example.demo.author;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

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
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Author> query = cb.createQuery(Author.class);
        Root<Author> root = query.from(Author.class);
        query.select(root);
        return em.createQuery(query).getResultList();
    }

    @Override
    public void saveAuthors(Author author) {
        em.persist(author);
    }

    @Override
    public Optional<Author> getAuthorByUUID(UUID uuid) {
        return Optional.ofNullable(em.find(Author.class, uuid));
    }

    @Override
    public Optional<Author> getAuthorByName(String name) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Author> query = cb.createQuery(Author.class);
            Root<Author> root = query.from(Author.class);
            query.where(cb.equal(root.get("name"), name));
            query.select(root);
            return Optional.of(em.createQuery(query)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
}
