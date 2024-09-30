package org.example;

import java.io.*;
import java.net.Socket;

public class MonoThreadClientHandler implements Runnable {
    private Socket clientSocket;

    public MonoThreadClientHandler(Socket socket) {
        this.clientSocket = socket;
    }
    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            System.out.println("New connection accepted. Port: " + clientSocket.getPort());
            String name = in.readLine();
            out.println(String.format(java.time.LocalTime.now() + " New person: " + name + " connected"));
            while (true) {
                    String message = in.readLine();
                if (message.equals("/exit")) {
                    System.out.println(clientSocket.getPort() + " has disconnected");
                    break;
                }
                out.println(String.format(java.time.LocalTime.now() + " " + name + ": " + message));
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
