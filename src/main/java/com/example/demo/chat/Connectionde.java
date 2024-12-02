package com.example.demo.chat;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import java.io.Serializable;

@RequestScoped
@Named
@Log
public class Connectionde {

    @Getter
    @Setter
    private String message;

    private final PushMessageContext pushMessageContext;

    private final SecurityContext securityContext;

    @Inject
    public Connectionde(PushMessageContext pushMessageContext,
                       @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext) {
        this.pushMessageContext = pushMessageContext;
        this.securityContext = securityContext;
    }

    public void sendMessage() {
        log.info(message);
        pushMessageContext.notifyAll(Message.builder()
                .from(securityContext.getCallerPrincipal().getName())
                .content(message)
                .build());
    }
}
