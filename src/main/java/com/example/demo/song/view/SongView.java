package com.example.demo.song.view;

import com.example.demo.song.SongDto;
import com.example.demo.song.SongService;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
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

    public void init() throws IOException {
        try {
            song = songService.findById(id);
        }catch(EJBException e){
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_FORBIDDEN, "Song not found");
        }
    }
}
