package com.example.demo.author;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AuthorRepository {

    private final Set<Author> authors= new HashSet<>();

    public List<Author> getAuthors() {
        return new ArrayList<>(authors);
    }

    public void saveAuthors(Author author) {
        authors.add(author);
    }
}
