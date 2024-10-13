package com.example.demo.controller;

import com.example.demo.author.Author;
import com.example.demo.author.AuthorCommand;
import com.example.demo.author.AuthorService;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = {
        "api/*"
})
@MultipartConfig(maxFileSize = 200 * 1024)
public class ApiServlet extends HttpServlet {

    private static final Pattern UUID = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");
    private static final Pattern AUTHOR_UUID = Pattern.compile("/authors/(%s)/avatar".formatted(UUID.pattern()));
    private static final Pattern AUTHOR = Pattern.compile("/authors/(%s)".formatted(UUID.pattern()));

    private AuthorService authorService;

    private final Jsonb jsonb = JsonbBuilder.create();


    @Override
    public void init() throws ServletException {
        authorService = (AuthorService) getServletContext().getAttribute("authorService");
        super.init();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("PATCH")) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
//        if(servletPath.equals("/api")) {
        if (path.matches("/authors/?")) {
            response.setContentType("application/json");
            response.getWriter().write(jsonb.toJson(authorService.findAll()));
            return;
        } else if (path.matches(AUTHOR_UUID.pattern())) {
            response.setContentType("image/png");
            UUID uuid = extractUuid(AUTHOR_UUID, path);
            byte[] portrait = authorService.findAuthorAvatar(uuid);
            response.setContentLength(portrait.length);
            response.getOutputStream().write(portrait);
            return;
        }
//        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        if (path.matches("/authors/")) {
            response.setContentType("application/json");
            Author author = Author.builder()
                    .name("dsgsghwt")
                    .build();
            System.out.println("xdf");
            authorService.create(author);
            response.getWriter().write(jsonb.toJson(authorService.findAll()));
            return;
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (path.matches(AUTHOR_UUID.pattern())) {
            UUID uuid = extractUuid(AUTHOR_UUID, path);
            System.out.println(uuid);
            authorService.updateAvatar(uuid, request.getPart("portrait").getInputStream());
            return;
        }
        System.out.println("TESTSTETESTS");
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (path.matches(AUTHOR.pattern())) {
            UUID uuid = extractUuid(AUTHOR, path);
            authorService.updateAuthor(uuid, jsonb.fromJson(request.getReader(), AuthorCommand.class));
            return;
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private String parseRequestPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        path = path != null ? path : "";
        return path;
    }

    private static UUID extractUuid(Pattern pattern, String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            return java.util.UUID.fromString(matcher.group(1));
        }
        throw new IllegalArgumentException("No UUID in path.");
    }
}
