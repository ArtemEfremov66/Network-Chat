package org.example;

import java.io.*;


public class Main {
    public static void main(String[] args) {
        try(BufferedReader reader = new BufferedReader(new FileReader("Settings.txt"))) {
            Server server = new Server(Integer.parseInt(reader.readLine()));
            server.start();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
}