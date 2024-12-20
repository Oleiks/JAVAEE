package com.example.demo.song.view;

import com.example.demo.song.SongDto;
import com.example.demo.song.SongService;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
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

    @Getter
    private SongDto songWrongEdit;

    @Getter
    private boolean wrong = false;

    @Getter
    private String message;

    @EJB
    public void setSongService(SongService songService) {
        this.songService = songService;
    }

    public void init() throws IOException {
        try {
            song = songService.findById(id);
            songWrongEdit = null;
        } catch (EJBException e) {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_FORBIDDEN, "Song not found");
        }
    }

    public String saveAction() throws IOException {
        try {
            String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
            songWrongEdit = SongDto.builder()
                    .title(song.getTitle())
                    .premiereDate(song.getPremiereDate())
                    .length(song.getLength())
                    .build();
            message = "";
            wrong = false;
            songService.updateSong(id, song);
            return viewId + "?faces-redirect=true&includeViewParams=true";
        } catch (EJBException ex) {
            song = songService.findById(id);
            message = "Song entity was changed by somebody else";
            wrong = true;
            if (ex.getCause() instanceof IllegalArgumentException) {
                message = ex.getMessage();
            }
            return null;
        }
    }
}
