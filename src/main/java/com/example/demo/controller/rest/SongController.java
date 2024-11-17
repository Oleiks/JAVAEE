package com.example.demo.controller.rest;

import com.example.demo.musicGenre.MusicGenre;
import com.example.demo.musicGenre.MusicGenreService;
import com.example.demo.song.PatchSongRequest;
import com.example.demo.song.PutSongRequest;
import com.example.demo.song.SongDto;
import com.example.demo.song.SongMapper;
import com.example.demo.song.SongService;
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
public class SongController {
    private final SongService songService;
    private final MusicGenreService musicGenreService;

    @Inject
    public SongController(SongService songService, MusicGenreService musicGenreService) {
        this.songService = songService;
        this.musicGenreService = musicGenreService;
    }

    @GET
    @Path("/{musicGenreId}/songs")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SongDto> getSongs(@PathParam("musicGenreId") UUID musicGenreId) {
        return songService.findAllByMusicGenreId(musicGenreId);
    }

    @GET
    @Path("/songs")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SongDto> getAllSongs() {
        return songService.findAll();
    }

    @GET
    @Path("/{musicGenreId}/songs/{songId}")
    @Produces(MediaType.APPLICATION_JSON)
    public SongDto getSong(@PathParam("musicGenreId") UUID musicGenreId, @PathParam("songId") UUID songId) {
        return songService.findByMusicGenreId(musicGenreId, songId);
    }

    @DELETE
    @Path("/{musicGenreId}/songs/{songId}")
    public Response deleteSong(@PathParam("musicGenreId") UUID musicGenreId, @PathParam("songId") UUID songId) {
        songService.delete(musicGenreId, songId);
        return Response.ok().build();
    }

    @PATCH
    @Path("/{musicGenreId}/songs/{songId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response patchSong(@PathParam("musicGenreId") UUID musicGenreId, @PathParam("songId") UUID songId, PatchSongRequest request) {
        songService.updateSong(songId, musicGenreId, request);
        return Response.ok().build();
    }

    @PUT
    @Path("/{musicGenreId}/songs")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putSong(@PathParam("musicGenreId") UUID musicGenreId, PutSongRequest request) {
        MusicGenre musicGenre = musicGenreService.find(musicGenreId);
        songService.create(SongMapper.toSong(request, musicGenre));
        return Response.ok().build();
    }
}
