package com.example.demo.configuration.listener;

import com.example.demo.author.Author;
import com.example.demo.author.AuthorService;
import com.example.demo.author.Type;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static com.example.demo.author.AuthorService.AVATAR_RESOURCE_FOLDER;

@WebListener
public class InitializedData implements ServletContextListener {

    private AuthorService authorService;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        authorService = (AuthorService) event.getServletContext().getAttribute("authorService");
        init();
    }

    private void init() {
        Author author = Author.builder()
                .id(UUID.fromString("bc72126e-9a5e-4304-a28a-df340312760f"))
                .name("Kanye West")
                .portrait(getResourceAsByteArray("bc72126e-9a5e-4304-a28a-df340312760f.png"))
                .debutYear(1996)
                .type(Type.SOLO)
                .build();
        Author author2 = Author.builder()
                .name("Linkin Park")
                .id(UUID.fromString("7e7ae796-759e-4075-bb41-b6b40cedfc18"))
                .portrait(getResourceAsByteArray("7e7ae796-759e-4075-bb41-b6b40cedfc18.png"))
                .debutYear(1996)
                .type(Type.BAND)
                .build();
        Author author3 = Author.builder()
                .id(UUID.fromString("a25b09aa-4c2b-4159-ab07-c1ca93108a4a"))
                .name("AC/DC")
                .portrait(getResourceAsByteArray("a25b09aa-4c2b-4159-ab07-c1ca93108a4a.png"))
                .debutYear(1973)
                .type(Type.BAND)
                .build();
        Author author4 = Author.builder()
                .name("Kendrick Lamar")
                .id(UUID.fromString("de8603ba-b47f-4ee2-9b70-12ba721649a9"))
                .portrait(getResourceAsByteArray("de8603ba-b47f-4ee2-9b70-12ba721649a9.png"))
                .debutYear(2003)
                .type(Type.SOLO)
                .build();
        authorService.create(author);
        authorService.create(author2);
        authorService.create(author3);
        authorService.create(author4);
        System.out.println(JsonbBuilder.create().toJson(authorService.findAll()));
    }

    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try {
            return Files.readAllBytes(Path.of(AVATAR_RESOURCE_FOLDER + name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
