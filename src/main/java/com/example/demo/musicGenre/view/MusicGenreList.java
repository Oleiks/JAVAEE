package com.example.demo.musicGenre.view;

import com.example.demo.interceptor.LoggerInt;
import com.example.demo.musicGenre.MusicGenreDto;
import com.example.demo.musicGenre.MusicGenreService;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@ViewScoped
@Named
public class MusicGenreList implements Serializable {

    private List<MusicGenreDto> genres;
    @Getter
    @Setter
    private MusicGenreDto filter = new MusicGenreDto();
    private MusicGenreService musicGenreService;

    @EJB
    public void setMusicGenreService(MusicGenreService musicGenreService) {
        this.musicGenreService = musicGenreService;
    }

    public List<MusicGenreDto> getGenres() {
        if (genres == null) {
            genres = musicGenreService.findAll();
        }
        return genres;
    }

    public void getGenresByFilter() {
        if ((filter.getGenre() == null || filter.getGenre().isEmpty()) && (filter.getYearOfOrigin() == null)) {
            genres = musicGenreService.findAll();
        } else {
            genres = musicGenreService.findAllByFilter(filter);
        }
    }

    @LoggerInt
    public void deleteAction(MusicGenreDto genre) {
        musicGenreService.delete(genre.getId());
    }
}
