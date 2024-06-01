package com.taxi.backend.controller;

import com.taxi.backend.utils.socket.SocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MessageController {

    @Autowired
    private SocketServer socketServer;

    @PostMapping("/send/{userId}")
    public String sendMessage(@PathVariable String userId, @RequestBody String message) {
        socketServer.sendMessageToUser(userId, message);
        return "Message sent to user " + userId;
    }
}
