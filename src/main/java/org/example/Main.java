package org.example;

import java.io.*;


public class Main {
    public static void main(String[] args) {
        Server server = new Server(8089);
        server.start();
    }
}