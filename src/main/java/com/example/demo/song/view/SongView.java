package com.example.demo.song.view;

import com.example.demo.song.SongDto;
import com.example.demo.song.SongService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.UUID;

@Named
@RequestScoped
public class SongView {

    @Setter
    @Getter
    private UUID id;

    @Getter
    private SongDto song;

    private final SongService songService;

    @Inject
    public SongView(SongService songService) {
        this.songService = songService;
    }

    public void init() throws IOException {
        song = songService.findById(id);
    }
}
