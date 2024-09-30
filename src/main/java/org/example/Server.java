package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public int port;
    static ExecutorService executorService = Executors.newFixedThreadPool(64);

    public Server(int port) {
        this.port = port;
    }
    public void start() {
        try (final ServerSocket serverSocket = new ServerSocket(port)) {
            while(!serverSocket.isClosed()) {
                final var socket = serverSocket.accept();
                executorService.execute(new MonoThreadClientHandler(socket));
                System.out.print("Connection accepted.");
            }
            executorService.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
