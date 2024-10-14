package com.example.demo.author;

import com.example.demo.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class AuthorService {

    private static final String AVATAR_RESOURCE_FOLDER = "C:\\JAVAEE\\JAVAEE\\src\\main\\resources\\com\\example\\demo\\configuration\\avatar\\";

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

    public synchronized byte[] findAuthorAvatar(UUID id) {
        return authorRepository.getAuthorByUUID(id).map(Author::getPortrait).orElseThrow(() -> new EntityNotFoundException("Author avatar not found"));
    }

    public synchronized void updateAvatar(UUID uuid, InputStream portrait) {
        Author author = find(uuid);
        Path path = Paths.get(AVATAR_RESOURCE_FOLDER + author.getId() + ".png");g
        try {
            byte[] buffer = portrait.readAllBytes();
            author.setPortrait(buffer);
            Files.copy(portrait, path, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public synchronized void deleteAvatar(UUID uuid) {
        Author author = find(uuid);
        Path path = Paths.get(AVATAR_RESOURCE_FOLDER + author.getId() + ".png");
        try {
            author.setPortrait(null);
            Files.delete(path);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public synchronized void updateAuthor(UUID uuid, AuthorCommand authorCommand) {
        Author author = find(uuid);
        System.out.println(authorCommand);
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
