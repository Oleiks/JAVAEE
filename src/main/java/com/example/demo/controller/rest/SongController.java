package com.example.demo.controller.rest;

import com.example.demo.musicGenre.MusicGenre;
import com.example.demo.musicGenre.MusicGenreService;
import com.example.demo.song.*;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
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
    public void deleteSong(@PathParam("musicGenreId") UUID musicGenreId, @PathParam("songId") UUID songId) {
        songService.delete(musicGenreId, songId);
    }

    @PATCH
    @Path("/{musicGenreId}/songs/{songId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void patchSong(@PathParam("musicGenreId") UUID musicGenreId, @PathParam("songId") UUID songId, PatchSongRequest request) {
        songService.updateSong(songId, musicGenreId, request);
    }

    @PUT
    @Path("/{musicGenreId}/songs")
    @Consumes(MediaType.APPLICATION_JSON)
    public void putSong(@PathParam("musicGenreId") UUID musicGenreId, PutSongRequest request) {
        MusicGenre musicGenre = musicGenreService.find(musicGenreId);
        songService.create(SongMapper.toSong(request, musicGenre));
    }
}
