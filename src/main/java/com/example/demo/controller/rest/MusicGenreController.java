package com.example.demo.controller.rest;

import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.musicGenre.MusicGenreDto;
import com.example.demo.musicGenre.MusicGenreMapper;
import com.example.demo.musicGenre.MusicGenreService;
import com.example.demo.musicGenre.PatchMusicGenreRequest;
import com.example.demo.musicGenre.PutMusicGenreRequest;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
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

import java.util.UUID;

@Path("/musicGenres")
public class MusicGenreController {
    private MusicGenreService musicGenreService;

    @EJB
    public void setService(MusicGenreService musicGenreService) {
        this.musicGenreService = musicGenreService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMusicGenres() {
        try {
            return Response.ok(musicGenreService.findAll()).build();
        } catch (EJBException e) {
            return Response.status(401).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMusicGenre(@PathParam("id") UUID id) {
        try{
            return Response.ok(musicGenreService.findById(id)).build();
        } catch (EntityNotFoundException e) {
            return Response.status(404).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteMusicGenre(@PathParam("id") UUID id) {
        try {
            musicGenreService.delete(id);
            return Response.ok().build();
        } catch (EJBException e) {
            return Response.status(401).build();
        }
    }

    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response patchMusicGenre(@PathParam("id") UUID id, PatchMusicGenreRequest request) {
        try {
            musicGenreService.updateMusicGenre(id, request);
            return Response.ok().build();
        } catch (EntityNotFoundException e){
            return Response.status(404).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putMusicGenre(PutMusicGenreRequest request) {
        try {
            musicGenreService.create(MusicGenreMapper.toMusicGenre(request));
            return Response.ok().build();
        } catch (EJBException e) {
            return Response.status(401).build();
        }
    }
}
