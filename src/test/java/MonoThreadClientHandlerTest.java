import org.example.MonoThreadClientHandler;
import org.example.Server;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.net.Socket;
import java.util.Date;

public class MonoThreadClientHandlerTest {
    Server server = new Server(6666);

    @Test
    public void newConnectedNameTest() throws IOException, InterruptedException {
        new Thread(() -> {
            server.start();
        }).start();
        Thread.sleep(1000);
        try(Socket socket = new Socket("localhost", 6666);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedWriter writer = new BufferedWriter(new FileWriter("fileTest.log", true));) {
            MonoThreadClientHandler thread = new MonoThreadClientHandler(socket);
            String input = "Test";
            String expectedOutput = new Date() + " New person: " + "Test" + " connected";
            String actualOutput = thread.newNameConnected(input, out, writer);
            Assertions.assertEquals(expectedOutput, actualOutput);
        }
    }
    @Test
    public void chatTest() throws IOException, InterruptedException {
        new Thread(() -> {
            server.start();
        }).start();
        Thread.sleep(1000);
        try(Socket socket = new Socket("localhost", 6666);
            BufferedReader in = new BufferedReader(new StringReader("/exit"));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedWriter writer = new BufferedWriter(new FileWriter("fileTest.log", true));) {
            MonoThreadClientHandler thread = new MonoThreadClientHandler(socket);
            String name = "Arti";

            String expected = "exit";
            String response = thread.chat(in, writer, name, out);
            Assertions.assertEquals(expected, response);
        }
    }
}
