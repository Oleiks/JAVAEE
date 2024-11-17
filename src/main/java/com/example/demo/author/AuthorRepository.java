package com.example.demo.author;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository {

    List<Author> getAuthors();

    void saveAuthors(Author author);

    Optional<Author> getAuthorByUUID(UUID uuid);
}
