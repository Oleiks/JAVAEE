package com.example.demo.configuration.listener;

import com.example.demo.author.Author;
import com.example.demo.author.AuthorService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
                .name("xd")
                .build();

        authorService.create(author);

    }

}
