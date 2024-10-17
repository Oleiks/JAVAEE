package com.example.demo.author;

import com.example.demo.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public synchronized List<AuthorDto> findAll() {
        return authorRepository.getAuthors().stream().map(AuthorMapper::toAuthorDto).toList();
    }

    public synchronized AuthorDto findById(UUID id) {
        return AuthorMapper.toAuthorDto(find(id));
    }

    public synchronized void create(Author author) {
        if (author.getId() != null) {
            authorRepository.saveAuthors(author);
        } else {
            author.setId(UUID.randomUUID());
            authorRepository.saveAuthors(author);
        }
    }

    public synchronized byte[] findAuthorAvatar(UUID id, String pathToAvatars) {
        try{
            return Files.readAllBytes(Path.of(pathToAvatars+id+".png"));
        } catch (IOException e) {
            throw new EntityNotFoundException("Avatar not found");
        }
    }

    public synchronized void updateAvatar(UUID uuid, InputStream portrait, String pathToAvatars) {
        Author author = find(uuid);
        Path path = Paths.get(pathToAvatars + author.getId() + ".png");
        try {
            byte[] buffer = portrait.readAllBytes();
            Files.write(path, buffer, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public synchronized void deleteAvatar(UUID uuid, String pathToAvatars) {
        Author author = find(uuid);
        Path path = Paths.get(pathToAvatars + author.getId() + ".png");
        try {
            Files.delete(path);
        } catch (Exception e) {
            throw new EntityNotFoundException("Avatar not found");
        }
    }

    public synchronized void updateAuthor(UUID uuid, AuthorCommand authorCommand) {
        Author author = find(uuid);
        if (author != null) {
            if (authorCommand.getAge() != null) {
                author.setDebutYear(authorCommand.getAge());
            }
            if (authorCommand.getName() != null) {
                author.setName(authorCommand.getName());
            }
        }
    }

    private Author find(UUID id) {
        return authorRepository.getAuthorByUUID(id)
                .orElseThrow(() -> new EntityNotFoundException("Author with id " + id + " not found"));
    }
}
