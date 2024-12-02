package com.example.demo.musicGenre.view;

import com.example.demo.musicGenre.MusicGenreDto;
import com.example.demo.musicGenre.MusicGenreService;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@RequestScoped
@Named
public class MusicGenreList implements Serializable {

    private List<MusicGenreDto> genres;
    private MusicGenreService musicGenreService;

    @EJB
    public void setMusicGenreService(MusicGenreService musicGenreService) {
        this.musicGenreService = musicGenreService;
    }

    public List<MusicGenreDto> getGenres() {
        if(genres == null) {
            genres=musicGenreService.findAll();
        }
        return genres;
    }

    public void deleteAction(MusicGenreDto genre) {
        musicGenreService.delete(genre.getId());
        genres=null;
    }
}
