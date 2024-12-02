package com.example.demo.chat;

import com.example.demo.chat.Message;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class ChatBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private String message;

    @Getter
    @Setter
    private String toUser;

    @Getter
    private final List<Message> messages = new ArrayList<>();

    @Inject
    private ChatService chatService;

    @Inject
    private SecurityContext securityContext;

    public void sendMessage() {
        String fromUser = securityContext.getCallerPrincipal().getName();
        Message msg = new Message().builder()
                .to(toUser)
                .from(fromUser)
                .content(message)
                .build();
        chatService.sendMessage(msg);
        messages.add(msg);
        message = "";
    }
}
