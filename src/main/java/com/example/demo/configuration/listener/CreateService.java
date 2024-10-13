package com.example.demo.configuration.listener;

import com.example.demo.author.AuthorRepository;
import com.example.demo.author.AuthorService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class CreateService implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        AuthorRepository aRepo = new AuthorRepository();
        event.getServletContext().setAttribute("authorService", new AuthorService(aRepo));
    }

}