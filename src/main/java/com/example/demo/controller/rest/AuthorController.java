package com.example.demo.controller.rest;

import com.example.demo.author.Author;
import com.example.demo.author.AuthorDto;
import com.example.demo.author.AuthorService;
import com.example.demo.musicGenre.MusicGenreDto;
import com.example.demo.musicGenre.MusicGenreMapper;
import com.example.demo.musicGenre.PatchMusicGenreRequest;
import com.example.demo.musicGenre.PutMusicGenreRequest;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.UUID;

@Path("/authors")
public class AuthorController {
    private AuthorService authorService;

    @EJB
    public void setService(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<AuthorDto> getAuthors() {
        return authorService.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public AuthorDto getAuthor(@PathParam("id") UUID id) {
        return authorService.findById(id);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putAuthor(Author request) {
        authorService.create(request);
    }
}
