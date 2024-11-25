package com.example.demo.configuration.singleton;

import com.example.demo.author.Author;
import com.example.demo.author.AuthorRepository;
import com.example.demo.author.UserRoles;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.List;
import java.util.UUID;

@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
@NoArgsConstructor(force = true)
public class InitializeAdminService {

    private final AuthorRepository authorRepository;

    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public InitializeAdminService(
            AuthorRepository authorRepository,
            @SuppressWarnings("CdiInjectionPointsInspection") Pbkdf2PasswordHash passwordHash
    ) {
        this.authorRepository = authorRepository;
        this.passwordHash = passwordHash;
    }

    @PostConstruct
    @SneakyThrows
    private void init() {
        Author admin = Author.builder()
                .id(UUID.fromString("14d59f3a-057c-44d5-825a-19295a6600a8"))
                .name("admin-service")
                .password(passwordHash.generate("adminadmin".toCharArray()))
                .roles(List.of(UserRoles.ADMIN, UserRoles.USER))
                .build();

        authorRepository.saveAuthors(admin);
    }
}
