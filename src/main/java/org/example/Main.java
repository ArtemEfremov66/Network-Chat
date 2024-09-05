package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Server started");
        int port = 8089;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    System.out.println("New connection accepted. Port: " + clientSocket.getPort());
                    String name = in.readLine();
                    out.println(String.format(java.time.LocalTime.now() + " New person: " + name + " connected"));

                    Runnable logic = () -> {
                        while (true) {
                            String message = null;
                            try {
                                message = in.readLine();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            if (message.equals("/exit")) {
                                System.out.println(clientSocket.getPort() + " has disconnected");
                                break;
                            }
                            out.println(String.format(java.time.LocalTime.now() + " " + name + ": " + message));
                        }
                    };
                    Thread thread = new Thread(logic);
                    thread.start();
                }
            }
        }
    }
}