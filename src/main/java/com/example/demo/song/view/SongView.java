package com.example.demo.song.view;

import com.example.demo.song.SongDto;
import com.example.demo.song.SongService;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
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
public class SongView implements Serializable {

    @Setter
    @Getter
    private UUID id;

    @Getter
    private SongDto song;

    private SongService songService;

    @EJB
    public void setSongService(SongService songService) {
        this.songService = songService;
    }

    public void init(){
        song = songService.findById(id);
    }
}
