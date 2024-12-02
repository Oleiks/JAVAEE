package com.example.demo.chat;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.push.Push;
import jakarta.faces.push.PushContext;
import jakarta.inject.Inject;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class ChatWebSocket {

    @Inject
    @Push(channel = "chat")
    private PushContext pushContext;

    public void onMessage(@Observes Message message) {
        if (message.getTo() == null || message.getTo().isEmpty()) {
            // Wysyłanie do wszystkich
            pushContext.send(message);
        } else {
            // Wysyłanie do konkretnego użytkownika
            pushContext.send(message, message.getTo());
        }
    }
}
