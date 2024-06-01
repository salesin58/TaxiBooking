package com.taxi.backend.utils.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket socket;
    private SocketServer server;

    public ClientHandler(Socket socket, SocketServer server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String userId = in.readLine();
            String socketId = Integer.toString(socket.hashCode()); // Socket bağlantısının benzersiz bir kimliği olacak
            server.addUserSocket(userId, socketId, socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}