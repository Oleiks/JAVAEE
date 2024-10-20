package com.example.demo.controller;

import com.example.demo.author.Author;
import com.example.demo.author.AuthorCommand;
import com.example.demo.author.AuthorService;
import com.example.demo.musicGenre.MusicGenreService;
import com.example.demo.song.SongService;
import jakarta.inject.Inject;
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

import static com.example.demo.controller.ApiServlet.API;

@WebServlet(urlPatterns = {
        API + "/*"
})
@MultipartConfig(maxFileSize = 200 * 1024)
public class ApiServlet extends HttpServlet {

    public static final String API = "/api";

    private static final Pattern UUID = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");
    private static final Pattern AUTHOR_PORTRAIT = Pattern.compile("/authors/(%s)/avatar".formatted(UUID.pattern()));
    private static final Pattern AUTHOR = Pattern.compile("/authors/(%s)".formatted(UUID.pattern()));
    private static final Pattern AUTHORS = Pattern.compile("/authors/");
    private static final Pattern MUSIC_GENRE = Pattern.compile("/musicGenres/(%s)".formatted(UUID.pattern()));
    private static final Pattern MUSIC_GENRES = Pattern.compile("/musicGenres/");
    private static final Pattern SONG = Pattern.compile("/songs/(%s)".formatted(UUID.pattern()));
    private static final Pattern SONGS = Pattern.compile("/songs/");

    private final Jsonb jsonb = JsonbBuilder.create();
    private String AUTHORS_FOLDER;

    private final AuthorService authorService;
    private final SongService songService;
    private final MusicGenreService musicGenreService;

    @Inject
    public ApiServlet(AuthorService authorService,
                      SongService songService,
                      MusicGenreService musicGenreService) {
        this.authorService = authorService;
        this.songService = songService;
        this.musicGenreService = musicGenreService;
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
        if (API.equals(servletPath)) {
            if (path.matches(AUTHORS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(authorService.findAll()));
                return;
            } else if (path.matches(AUTHOR_PORTRAIT.pattern())) {
                response.setContentType("image/png");
                UUID uuid = extractUuid(AUTHOR_PORTRAIT, path);
                byte[] portrait = authorService.findAuthorAvatar(uuid);
                response.setContentLength(portrait.length);
                response.getOutputStream().write(portrait);
                return;
            } else if (path.matches(AUTHOR.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(AUTHOR, path);
                response.getWriter().write(jsonb.toJson(authorService.findById(uuid)));
                return;
            } else if (path.matches(SONGS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(songService.findAll()));
                return;
            } else if (path.matches(SONG.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(SONG, path);
                response.getWriter().write(jsonb.toJson(songService.findById(uuid)));
                return;
            } else if (path.matches(MUSIC_GENRES.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(musicGenreService.findAll()));
                return;
            } else if (path.matches(MUSIC_GENRE.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(MUSIC_GENRE, path);
                response.getWriter().write(jsonb.toJson(musicGenreService.findById(uuid)));
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (API.equals(servletPath)) {
            if (path.matches(AUTHORS.pattern())) {
                response.setContentType("application/json");
                authorService.create(jsonb.fromJson(request.getReader(), Author.class));
                response.getWriter().write(jsonb.toJson(authorService.findAll()));
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (API.equals(servletPath)) {
            if (path.matches(AUTHOR_PORTRAIT.pattern())) {
                UUID uuid = extractUuid(AUTHOR_PORTRAIT, path);
                authorService.updateAvatar(uuid, request.getPart("avatar").getInputStream());
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (API.equals(servletPath)) {
            if (path.matches(AUTHOR_PORTRAIT.pattern())) {
                UUID uuid = extractUuid(AUTHOR_PORTRAIT, path);
                authorService.deleteAvatar(uuid);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (API.equals(servletPath)) {
            if (path.matches(AUTHOR.pattern())) {
                UUID uuid = extractUuid(AUTHOR, path);
                authorService.updateAuthor(uuid, jsonb.fromJson(request.getReader(), AuthorCommand.class));
                return;
            }
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
