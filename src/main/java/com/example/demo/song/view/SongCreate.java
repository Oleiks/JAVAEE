package com.example.demo.song.view;

import com.example.demo.musicGenre.MusicGenreDto;
import com.example.demo.musicGenre.MusicGenreService;
import com.example.demo.song.SongCommand;
import com.example.demo.song.SongService;
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

    private final SongService songService;

    private final MusicGenreService musicGenreService;

    @Getter
    private final List<MusicGenreDto> musicGenres;

    private SongCommand song;

    @Inject
    public SongCreate(SongService songService, MusicGenreService musicGenreService) {
        this.songService = songService;
        this.musicGenreService = musicGenreService;
        musicGenres = musicGenreService.findAll();
    }

    public SongCommand getSong() {
        if (song == null) {
            song = new SongCommand();
            song.setMusicGenre(UUID.randomUUID());
        }
        return song;
    }

    public String saveAction() {
        System.out.println("save action");
        songService.createSong(song);
        return "music_genre_list?faces-redirect=true";
    }
}
