package com.example.demo.configuration.listener;

import com.example.demo.author.Author;
import com.example.demo.author.AuthorService;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.SneakyThrows;

import java.io.InputStream;

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
                .name("1")
                .portrait(getResourceAsByteArray("../avatar/1.png"))
                .age(23)
                .build();
        authorService.create(author);
        Author author2 = Author.builder()
                .name("2")
                .portrait(getResourceAsByteArray("../avatar/2.png"))
                .age(6)
                .build();
        authorService.create(author2);
        System.out.println(JsonbBuilder.create().toJson(authorService.findAll()));
    }

    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            if (is != null) {
                return is.readAllBytes();
            } else {
                throw new IllegalStateException("Unable to get resource %s".formatted(name));
            }
        }
    }
}
