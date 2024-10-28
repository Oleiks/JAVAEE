package com.example.demo.musicGenre.view;

import com.example.demo.musicGenre.MusicGenreDto;
import com.example.demo.musicGenre.MusicGenreService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.UUID;

@Named
@RequestScoped
public class MusicGenreView {

    @Setter
    @Getter
    private UUID id;

    @Getter
    private MusicGenreDto musicGenre;

    private final MusicGenreService musicGenreService;

    @Inject
    public MusicGenreView(MusicGenreService musicGenreService) {
        this.musicGenreService = musicGenreService;
    }

    public void init() throws IOException {
        musicGenre = musicGenreService.findById(id);
    }
}
