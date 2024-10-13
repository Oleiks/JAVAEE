package com.example.demo.author;

import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public synchronized List<AuthorDto> findAll() {
        return authorRepository.getAuthors().stream().map(AuthorMapper::toAuthorDto).toList();
    }

    public synchronized Optional<Author> find(UUID id) {
        return authorRepository.getAuthorByUUID(id);
    }

    public synchronized void create(Author author) {
        if (author.getId() != null) {
            authorRepository.saveAuthors(author);
        } else {
            author.setId(UUID.randomUUID());
            authorRepository.saveAuthors(author);
        }
    }


    public synchronized void updateAvatar(Author author) {
        if (author.getId() != null) {
            authorRepository.saveAuthors(author);
        } else {
            author.setId(UUID.randomUUID());
            authorRepository.saveAuthors(author);
        }
    }

    public synchronized byte[] findAuthorAvatar(UUID id) {
        return authorRepository.getAuthorByUUID(id).map(Author::getPortrait).orElse(null);
    }

    public synchronized void updateAvatar(UUID uuid, InputStream portrait) {
        Author author = find(uuid).isPresent() ? find(uuid).get() : null;
        try {
            byte[] buffer = portrait.readAllBytes();
            author.setPortrait(buffer);
            File targetFile = new File("C:\\JAVAEE\\JAVAEE\\src\\main\\resources\\com\\example\\demo\\configuration\\avatar\\" + author.getId() + ".png");
            OutputStream outStream = new FileOutputStream(targetFile);
            outStream.write(buffer);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public synchronized void updateAuthor(UUID uuid, AuthorCommand authorCommand) {
        Author author = find(uuid).isPresent() ? find(uuid).get() : null;
        System.out.println(authorCommand);
        if (author != null) {
            if (authorCommand.getAge() != null) {
                author.setAge(authorCommand.getAge());
            }
            if (authorCommand.getName() != null) {
                author.setName(authorCommand.getName());
            }
        }
    }
}
