package com.example.demo.chat;

import com.example.demo.chat.Message;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@ApplicationScoped
public class ChatService {

    @Inject
    private Event<Message> messageEvent;

    public void sendMessage(Message message) {
        messageEvent.fire(message);
    }
}
