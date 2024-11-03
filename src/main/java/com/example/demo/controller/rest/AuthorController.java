package com.example.demo.controller.rest;

import com.example.demo.author.AuthorDto;
import com.example.demo.author.AuthorService;
import com.example.demo.musicGenre.MusicGenreDto;
import com.example.demo.musicGenre.MusicGenreMapper;
import com.example.demo.musicGenre.PatchMusicGenreRequest;
import com.example.demo.musicGenre.PutMusicGenreRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.UUID;

@Path("/authors")
public class AuthorController {
    private final AuthorService authorService;

    @Inject
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<AuthorDto> getMusicGenres() {
        return authorService.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public AuthorDto getMusicGenre(@PathParam("id") UUID id) {
        return authorService.findById(id);
    }

//    @DELETE
//    @Path("/{id}")
//    public void deleteMusicGenre(@PathParam("id") UUID id) {
//        authorService.delete(id);
//    }
//
//    @PATCH
//    @Path("/{id}")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public void patchMusicGenre(@PathParam("id") UUID id, PatchMusicGenreRequest request) {
//        authorService.updateMusicGenre(id, request);
//    }
//
//    @PUT
//    @Consumes(MediaType.APPLICATION_JSON)
//    public void putMusicGenre(PutMusicGenreRequest request) {
//        authorService.create(MusicGenreMapper.toMusicGenre(request));
//    }
}
