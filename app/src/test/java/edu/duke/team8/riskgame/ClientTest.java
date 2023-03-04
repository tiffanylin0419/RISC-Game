package edu.duke.team8.riskgame;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.jupiter.api.Test;

public class ClientTest {
    @Test()
    public void testIOException() throws IOException {
        ServerSocket ss = new ServerSocket(1239);
        Map m = new Game1Map();
        m.addTerritory(new BasicTerritory("Planto"));

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        Thread serverThread = new Thread(() -> {
            Server s = new Server(ss, m);
            s.run();
        });
        serverThread.start();

        Socket cl_s = new Socket("localhost", 1239);
        Client cli = new Client(cl_s, output);
        cl_s.close();
        cli.run();
        ss.close();
        assertEquals("Out/Input stream error\n", bytes.toString());
    }
    @Test
    public void testRun() throws Exception {
        ServerSocket ss = new ServerSocket(1244);
        Map m = new Game1Map();
        m.addTerritory(new BasicTerritory("Planto"));

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        Thread serverThread = new Thread(() -> {
            Server s = new Server(ss, m);
            s.run();
        });
        serverThread.start();

        Client cli = new Client(1244, "localhost", output);
        cli.run();
        serverThread.join();
        ss.close();
        assertEquals("Planto\n", bytes.toString());

    }
    @Test
    public void testReceive() throws Exception {
        ServerSocket ss = new ServerSocket(1234);
        Map m = new Game1Map();
        m.addTerritory(new BasicTerritory("Planto"));
        m.addTerritory(new BasicTerritory("Dova"));
        m.addTerritory(new BasicTerritory("Aova"));
        m.addTerritory(new BasicTerritory("Bova"));

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        Thread serverThread = new Thread(() -> {
            Server s = new Server(ss, m);
            s.run();
        });
        serverThread.start();

        Client cli = new Client(1234, "localhost", output);
        String res = cli.receive();
        serverThread.join();
        ss.close();
        assertEquals("Planto\nDova\nAova\nBova\n", res);

    }

}
