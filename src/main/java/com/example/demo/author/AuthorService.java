package com.example.demo.author;

import com.example.demo.exception.EntityNotFoundException;
import jakarta.annotation.Resource;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Resource(name = "fileLocation")
    private String fileLocation;

    @Inject
    private Pbkdf2PasswordHash passwordHash;

    @Inject
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @RolesAllowed(UserRoles.ADMIN)
    public List<AuthorDto> findAll() {
        return authorRepository.getAuthors().stream()
                .map(AuthorMapper::toAuthorDto).toList();
    }

    public AuthorDto findById(UUID id) {
        return AuthorMapper.toAuthorDto(find(id));
    }

    @RolesAllowed(UserRoles.USER)
    public void create(Author author) {
        author.setPassword(passwordHash.generate(author.getPassword().toCharArray()));
        authorRepository.saveAuthors(author);
    }

    public byte[] findAuthorAvatar(UUID id) {
        try {
            System.out.println(fileLocation + id + ".png");
            return Files.readAllBytes(Path.of(fileLocation + id + ".png"));
        } catch (IOException e) {
            throw new EntityNotFoundException("Avatar not found");
        }
    }

    public void updateAvatar(UUID uuid, InputStream portrait) {
        Author author = find(uuid);
        Path path = Paths.get(fileLocation + author.getId() + ".png");
        try {
            byte[] buffer = portrait.readAllBytes();
            Files.write(path, buffer, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public void deleteAvatar(UUID uuid) {
        Author author = find(uuid);
        Path path = Paths.get(fileLocation + author.getId() + ".png");
        try {
            Files.delete(path);
        } catch (Exception e) {
            throw new EntityNotFoundException("Avatar not found");
        }
    }

    public void updateAuthor(UUID uuid, AuthorCommand authorCommand) {
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

    public Author find(UUID id) {
        return authorRepository.getAuthorByUUID(id)
                .orElseThrow(() -> new EntityNotFoundException("Author with uuid " + id + " not found"));
    }
}
