package com.example.demo.author;

import java.util.List;
import java.util.UUID;

public interface AuthorRepository {

    List<Author> getAuthors();

    void saveAuthors(Author author);

    Author getAuthorByUUID(UUID uuid);
}
