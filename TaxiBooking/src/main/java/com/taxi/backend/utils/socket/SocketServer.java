package com.taxi.backend.utils.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SocketServer extends Thread {
    private ServerSocket serverSocket;
    private Map<String, Socket> socketMap;
    private Map<String, String> socketUserMap;

    public SocketServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        socketMap = new HashMap<>();
        socketUserMap = new HashMap<>();
    }

    public void run() {
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket, this).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addUserSocket(String userId, String socketId, Socket socket) {
        synchronized (socketMap) {
            socketMap.put(socketId, socket);
        }
        synchronized (socketUserMap) {
            socketUserMap.put(socketId, userId);
        }
    }

    public void sendMessageToUser(String userId, String message) {
        synchronized (socketUserMap) {
            for (Map.Entry<String, String> entry : socketUserMap.entrySet()) {
                if (entry.getValue().equals(userId)) {
                    String socketId = entry.getKey();
                    sendMessageToSocket(socketId, message);
                }
            }
        }
    }

    private void sendMessageToSocket(String socketId, String message) {
        synchronized (socketMap) {
            Socket client = socketMap.get(socketId);
            if (client != null) {
                try {
                    PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                    out.println(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
