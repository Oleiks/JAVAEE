package com.example.demo.song.view;

import com.example.demo.song.SongDto;
import com.example.demo.song.SongService;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

@ViewScoped
@Named
public class SongEdit implements Serializable {

    private SongService songService;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private SongDto song;

    @EJB
    public void setSongService(SongService songService) {
        this.songService = songService;
    }

    @PostConstruct
    public void init(){
        song = songService.findById(id);
    }

    public String saveAction() {
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        songService.updateSong(id, song);
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }
}
