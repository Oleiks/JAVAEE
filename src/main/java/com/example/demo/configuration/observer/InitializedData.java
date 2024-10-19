package com.example.demo.configuration.observer;

import com.example.demo.author.Author;
import com.example.demo.author.AuthorService;
import com.example.demo.author.Type;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.SneakyThrows;

import java.util.UUID;

@ApplicationScoped
public class InitializedData implements ServletContextListener {

    private final AuthorService authorService;
    private final RequestContextController requestContextController;

    @Inject
    public InitializedData(AuthorService authorService, RequestContextController requestContextController) {
        this.authorService = authorService;
        this.requestContextController=requestContextController;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    @SneakyThrows
    private void init() {
        requestContextController.activate();
        Author author = Author.builder()
                .id(UUID.fromString("bc72126e-9a5e-4304-a28a-df340312760f"))
                .name("Kanye West")
                .debutYear(1996)
                .type(Type.SOLO)
                .build();
        Author author2 = Author.builder()
                .name("Linkin Park")
                .id(UUID.fromString("7e7ae796-759e-4075-bb41-b6b40cedfc18"))
                .debutYear(1996)
                .type(Type.BAND)
                .build();
        Author author3 = Author.builder()
                .id(UUID.fromString("a25b09aa-4c2b-4159-ab07-c1ca93108a4a"))
                .name("AC/DC")
                .debutYear(1973)
                .type(Type.BAND)
                .build();
        Author author4 = Author.builder()
                .name("Kendrick Lamar")
                .id(UUID.fromString("de8603ba-b47f-4ee2-9b70-12ba721649a9"))
                .debutYear(2003)
                .type(Type.SOLO)
                .build();
        authorService.create(author);
        authorService.create(author2);
        authorService.create(author3);
        authorService.create(author4);
        requestContextController.deactivate();
    }
}
