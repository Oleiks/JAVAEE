package com.example.demo.song.view;

import com.example.demo.musicGenre.MusicGenreDto;
import com.example.demo.musicGenre.MusicGenreService;
import com.example.demo.song.SongCommand;
import com.example.demo.song.SongService;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@ViewScoped
@Named
public class SongCreate implements Serializable {

    private SongService songService;

    private MusicGenreService musicGenreService;

    @Getter
    private List<MusicGenreDto> musicGenres;

    private SongCommand song;

    @EJB
    public void setMusicGenreService(MusicGenreService musicGenreService) {
        this.musicGenreService = musicGenreService;
    }

    @EJB
    public void setSongService(SongService songService) {
        this.songService = songService;
    }

    public List<MusicGenreDto> init(){
        musicGenres = musicGenreService.findAll();
        return musicGenres;
    }

    public SongCommand getSong() {
        if (song == null) {
            song = new SongCommand();
            song.setMusicGenre(UUID.randomUUID());
        }
        return song;
    }

    public String saveAction() {
        songService.createSong(song);
        return "music_genre_list?faces-redirect=true";
    }
}
