package com.example.demo.author;

import com.example.demo.exception.EntityNotFoundException;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Resource(name = "fileLocation")
    private String fileLocation;

    @Inject
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public synchronized List<AuthorDto> findAll() {
        return authorRepository.getAuthors().stream()
                .map(AuthorMapper::toAuthorDto).toList();
    }

    public synchronized AuthorDto findById(UUID id) {
        return AuthorMapper.toAuthorDto(find(id));
    }

    public synchronized void create(Author author) {
        authorRepository.saveAuthors(author);
    }

    public synchronized byte[] findAuthorAvatar(UUID id) {
        try {
            System.out.println(fileLocation + id + ".png");
            return Files.readAllBytes(Path.of(fileLocation + id + ".png"));
        } catch (IOException e) {
            throw new EntityNotFoundException("Avatar not found");
        }
    }

    public synchronized void updateAvatar(UUID uuid, InputStream portrait) {
        Author author = find(uuid);
        Path path = Paths.get(fileLocation + author.getId() + ".png");
        try {
            byte[] buffer = portrait.readAllBytes();
            Files.write(path, buffer, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public synchronized void deleteAvatar(UUID uuid) {
        Author author = find(uuid);
        Path path = Paths.get(fileLocation + author.getId() + ".png");
        try {
            Files.delete(path);
        } catch (Exception e) {
            throw new EntityNotFoundException("Avatar not found");
        }
    }

    public synchronized void updateAuthor(UUID uuid, AuthorCommand authorCommand) {
        Author author = find(uuid);
        if (authorCommand.getDebutYear() != null) {
            author.setDebutYear(authorCommand.getDebutYear());
        }
        if (authorCommand.getName() != null) {
            author.setName(authorCommand.getName());
        }
        if (authorCommand.getType() != null) {
            author.setType(authorCommand.getType());
        }
    }

    private Author find(UUID id) {
        return authorRepository.getAuthorByUUID(id);
    }
}
