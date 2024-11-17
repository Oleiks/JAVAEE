package com.example.demo.controller.rest;

import com.example.demo.musicGenre.MusicGenreDto;
import com.example.demo.musicGenre.MusicGenreMapper;
import com.example.demo.musicGenre.MusicGenreService;
import com.example.demo.musicGenre.PatchMusicGenreRequest;
import com.example.demo.musicGenre.PutMusicGenreRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
    public Response deleteMusicGenre(@PathParam("id") UUID id) {
        musicGenreService.delete(id);
        return Response.ok().build();
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response patchMusicGenre(@PathParam("id") UUID id, PatchMusicGenreRequest request) {
        musicGenreService.updateMusicGenre(id, request);
        return Response.ok().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putMusicGenre(PutMusicGenreRequest request) {
        musicGenreService.create(MusicGenreMapper.toMusicGenre(request));
        return Response.ok().build();
    }
}
