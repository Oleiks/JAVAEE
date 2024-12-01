package com.example.demo.controller.rest;

import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.musicGenre.MusicGenre;
import com.example.demo.musicGenre.MusicGenreService;
import com.example.demo.song.PatchSongRequest;
import com.example.demo.song.PutSongRequest;
import com.example.demo.song.SongDto;
import com.example.demo.song.SongMapper;
import com.example.demo.song.SongService;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/musicGenres")
public class SongController {
    private SongService songService;
    private MusicGenreService musicGenreService;

    @EJB
    public void setMusicGenreService(MusicGenreService musicGenreService) {
        this.musicGenreService = musicGenreService;
    }

    @EJB
    public void setSongService(SongService songService) {
        this.songService = songService;
    }

    @GET
    @Path("/{musicGenreId}/songs")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSongs(@PathParam("musicGenreId") UUID musicGenreId) {
        try {
            return Response.ok(songService.findAllByMusicGenreId(musicGenreId)).build();
        } catch (EntityNotFoundException e) {
            return Response.status(404).build();
        }
    }

    @GET
    @Path("/songs")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSongs() {
        try {
            return Response.ok(songService.findAll()).build();
        } catch (EJBException e) {
            return Response.status(401).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{musicGenreId}/songs/{songId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSong(@PathParam("musicGenreId") UUID musicGenreId, @PathParam("songId") UUID songId) {
        try {
            return Response.ok(songService.findByIdC(musicGenreId, songId)).build();
        } catch (Exception e) {
            return Response.status(404).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{musicGenreId}/songs/{songId}")
    public Response deleteSong(@PathParam("musicGenreId") UUID musicGenreId, @PathParam("songId") UUID songId) {
        try {
            songService.delete(musicGenreId, songId);
            return Response.ok().build();
        } catch (EJBException e) {
            return Response.status(401).entity(e.getMessage()).build();
        } catch (EntityNotFoundException e) {
            return Response.status(404).entity(e.getMessage()).build();
        } catch (NotAuthorizedException e) {
            return Response.status(403).entity(e.getMessage()).build();
        }
    }

    @PATCH
    @Path("/{musicGenreId}/songs/{songId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response patchSong(@PathParam("musicGenreId") UUID musicGenreId, @PathParam("songId") UUID songId, PatchSongRequest request) {
        try {
            songService.updateSong(songId, musicGenreId, request);
            return Response.ok().build();
        } catch (EJBException e) {
            return Response.status(401).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(400).entity(e.getMessage()).build();
        } catch (EntityNotFoundException e) {
            return Response.status(404).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{musicGenreId}/songs")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putSong(@PathParam("musicGenreId") UUID musicGenreId, PutSongRequest request) {
        try {
            MusicGenre musicGenre = musicGenreService.find(musicGenreId);
            songService.createSongs(SongMapper.toSong(request, musicGenre));
            return Response.ok().build();
        } catch (EJBException e) {
            return Response.status(401).entity(e.getMessage()).build();
        } catch (IllegalArgumentException e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }
}
