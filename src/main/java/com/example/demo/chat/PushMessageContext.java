package com.example.demo.chat;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.push.Push;
import jakarta.faces.push.PushContext;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;

@ApplicationScoped
@Log
@NoArgsConstructor(force = true)
public class PushMessageContext {

    private PushContext broadcastChannel;

    private PushContext userChannel;

    private SecurityContext securityContext;

    @Getter
    @Setter
    private Message message;

    @Inject
    public PushMessageContext(
            @Push(channel = "broadcastChannel") PushContext broadcastChannel,
            @Push(channel = "userChannel") PushContext userChannel,
            @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext
    ) {
        this.broadcastChannel = broadcastChannel;
        this.userChannel = userChannel;
        this.securityContext = securityContext;
    }

    public void notifyAll(Message message) {
        broadcastChannel.send(message);
    }

    public void notifyUser(Message message, String username) {
        userChannel.send(message, username);
    }

}
