package org.example;

import java.io.*;
import java.net.Socket;

public class MonoThreadClientHandler implements Runnable {
    private final Socket clientSocket;

    public MonoThreadClientHandler(Socket socket) {
        this.clientSocket = socket;
    }
    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new FileWriter("file.log", true))) {
            System.out.println("New connection accepted. Port: " + clientSocket.getPort());
            writer.write("New connection accepted. Port: " + clientSocket.getPort() + "\n");
            String name = in.readLine();
            out.println(String.format(java.time.LocalTime.now() + " New person: " + name + " connected"));
            writer.write(java.time.LocalTime.now() + " New person: " + name + " connected" + "\n");
            while (true) {
                    String message = in.readLine();
                    writer.write(String.format(java.time.LocalTime.now() + " " + name + ": " + message) + "\n");
                if (message.equals("/exit")) {
                    System.out.println(clientSocket.getPort() + " has disconnected");
                    writer.write(clientSocket.getPort() + " has disconnected");
                    break;
                }
                out.println(String.format(java.time.LocalTime.now() + " " + name + ": " + message));
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
