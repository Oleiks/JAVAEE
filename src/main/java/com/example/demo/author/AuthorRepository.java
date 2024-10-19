package com.example.demo.author;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@ApplicationScoped
public class AuthorRepository {

    private final Set<Author> authors = new HashSet<>();

    public List<Author> getAuthors() {
        return new ArrayList<>(authors);
    }

    public void saveAuthors(Author author) {
        authors.add(author);
    }

    public Optional<Author> getAuthorByUUID(UUID uuid) {
        return getAuthors().stream().filter(author -> author.getId().equals(uuid)).findFirst();
    }
}
