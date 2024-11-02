package com.example.demo.controller.rest;

import com.example.demo.musicGenre.*;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.UUID;

@Path("/musicGenres")
public class MusicGenreController {
    private final MusicGenreService musicGenreService;

    @Inject
    public MusicGenreController(MusicGenreService musicGenreService) {
        this.musicGenreService = musicGenreService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<MusicGenreDto> getMusicGenres() {
        return musicGenreService.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public MusicGenreDto getMusicGenre(@PathParam("id") UUID id) {
        return musicGenreService.findById(id);
    }

    @DELETE
    @Path("/{id}")
    public void deleteMusicGenre(@PathParam("id") UUID id) {
        musicGenreService.delete(id);
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void patchMusicGenre(@PathParam("id") UUID id, PatchMusicGenreRequest request) {
        musicGenreService.updateMusicGenre(id, request);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putMusicGenre(PutMusicGenreRequest request) {
        musicGenreService.create(MusicGenreMapper.toMusicGenre(request));
    }
}
