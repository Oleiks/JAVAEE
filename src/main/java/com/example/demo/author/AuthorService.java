package com.example.demo.author;

import java.util.List;

public class AuthorService {

    private final AuthorRepository authorRepository = new AuthorRepository();

    public List<Author> findAll()
    {
        return authorRepository.getAuthors();
    }

    public void create(Author author){
        authorRepository.saveAuthors(author);
    }

}
