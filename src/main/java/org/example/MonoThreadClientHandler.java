package org.example;

import java.io.*;
import java.net.Socket;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
            String newNameConnected = newNameConnected(name, out, writer);
            chat(in, writer, name, out);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
    public String newNameConnected(String name, PrintWriter out, BufferedWriter writer) throws IOException {
        String newName = new Date() + " New person: " + name + " connected";
        out.println(String.format(newName));
        writer.write(newName);
        return newName;
    }
    public String chat(BufferedReader in, BufferedWriter writer, String name, PrintWriter out) throws IOException {
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
        return "exit";
    }
}
