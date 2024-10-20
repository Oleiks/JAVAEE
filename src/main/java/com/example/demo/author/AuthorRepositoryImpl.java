package com.example.demo.author;

import com.example.demo.dataStore.DataStore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class AuthorRepositoryImpl implements AuthorRepository {

    private final DataStore dataStore;

    @Inject
    public AuthorRepositoryImpl(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public List<Author> getAuthors() {
        return dataStore.getAuthors();
    }

    @Override
    public void saveAuthors(Author author) {
        dataStore.saveAuthors(author);
    }

    @Override
    public Author getAuthorByUUID(UUID uuid) {
        return dataStore.getAuthorByUUID(uuid);
    }
}
