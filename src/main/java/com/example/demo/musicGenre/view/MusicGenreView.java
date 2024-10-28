package com.example.demo.musicGenre.view;

import com.example.demo.musicGenre.MusicGenreDto;
import com.example.demo.musicGenre.MusicGenreService;
import com.example.demo.song.SongDto;
import com.example.demo.song.SongService;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

@Named
@ViewScoped
public class MusicGenreView implements Serializable {

    @Setter
    @Getter
    private UUID id;

    @Getter
    private MusicGenreDto musicGenre;

    private final MusicGenreService musicGenreService;
    private final SongService songService;

    @Inject
    public MusicGenreView(MusicGenreService musicGenreService, SongService songService) {
        this.musicGenreService = musicGenreService;
        this.songService = songService;
    }

    public void init() throws IOException {
        musicGenre = musicGenreService.findById(id);
    }

    public String deleteAction(SongDto song) {
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        songService.delete(song.getId());
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }
}