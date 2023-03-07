package edu.duke.team8.riskgame;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.jupiter.api.Test;

public class ClientTest {
    @Test
    public void testConstructor() throws Exception {
        ServerSocket ss = new ServerSocket(1237);
        Map m = new Game1Map();
        m.addTerritory(new BasicTerritory("Planto"));
        Server s = new Server(ss, m, 1);
        Thread serverThread = new Thread(() -> {
            s.run();
        });
        serverThread.start();

        Client c = new Client(1237, "localhost");
        s.stop();
        serverThread.join();
        ss.close();
    }
    @Test()
    public void testIOException() throws Exception {
        ServerSocket ss = new ServerSocket(1239);
        Map m = new Game1Map();
        m.addTerritory(new BasicTerritory("Planto"));

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        Server s = new Server(ss, m, 1);
        Thread serverThread = new Thread(() -> {
            s.run();
        });
        serverThread.start();

        Socket cl_s = new Socket("localhost", 1239);
        Client cli = new Client(cl_s, output);
        cl_s.close();
        cli.run();
        s.stop();
        serverThread.join();
        ss.close();
        String actual = bytes.toString().replaceAll("\\r\\n|\\r|\\n", "\n");
        assertEquals("Out/Input stream error\n", actual);
    }
    @Test
    public void testRun() throws Exception {
        ServerSocket ss = new ServerSocket(1244);
        Map m = new Game1Map();
        m.addTerritory(new BasicTerritory("Planto"));

        Server s = new Server(ss, m, 2);
        Thread serverThread = new Thread(() -> {
            s.run();
        });
        serverThread.start();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        Socket client = new Socket("localhost", 1244);
        Client cli = new Client(client, output);
        cli.run();
        String actual = bytes.toString().replaceAll("\\r\\n|\\r|\\n", "\n");
        assertEquals("Green\n0 units in Planto\n", actual);


        ByteArrayOutputStream bytes1 = new ByteArrayOutputStream();
        PrintStream output1 = new PrintStream(bytes1, true);
        Socket client1 = new Socket("localhost", 1244);
        Client cli1 = new Client(client1, output1);
        cli1.run();
        String actual1 = bytes1.toString().replaceAll("\\r\\n|\\r|\\n", "\n");
        assertEquals("Red\n0 units in Planto\n", actual1);

        s.stop();
        serverThread.join();
        ss.close();

    }
    @Test
    public void testReceive() throws Exception {
        ServerSocket ss = new ServerSocket(1234);
        Map m = new Game1Map();
        m.addTerritory(new BasicTerritory("Planto"));
        m.addTerritory(new BasicTerritory("Dova"));
        m.addTerritory(new BasicTerritory("Aova"));

        Territory t = new BasicTerritory("Grand");
        Player p = new TextPlayer("Green");
        Unit u = new BasicUnit(2, p);
        t.moveIn(u);
        m.addTerritory(t);

        Server s = new Server(ss, m, 1);
        Thread serverThread = new Thread(() -> {
            s.run();
        });
        serverThread.start();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        Client cli = new Client(1234, "localhost", output);
        cli.receive();
        cli.display();
        serverThread.interrupt();
        s.stop();
        serverThread.join();
        ss.close();
        String actual = bytes.toString().replaceAll("\\r\\n|\\r|\\n", "\n");
        assertEquals("Green\n0 units in Planto\n0 units in Dova\n" +
                "0 units in Aova\n2 units in Grand\n", actual);

    }
    @Test
    public void testDisplay() throws Exception {
        ServerSocket ss = new ServerSocket(1334);
        Map m = new Game1Map();
        m.addTerritory(new BasicTerritory("Planto"));
        Server s = new Server(ss, m, 1);
        Thread serverThread = new Thread(() -> {
            s.run();
        });
        serverThread.start();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        Client cli = new Client(1334, "localhost", output);
        cli.display();
        String actual = bytes.toString().replaceAll("\\r\\n|\\r|\\n", "\n");
        assertEquals("unassigned\n", actual);

        s.stop();
        serverThread.join();
        ss.close();
    }
    @Test
    public void testDisplayMap() throws Exception {
        ServerSocket ss = new ServerSocket(1324);
        Map m = new Game1Map();
        m.addTerritory(new BasicTerritory("Planto"));
        Server s = new Server(ss, m, 1);
        Thread serverThread = new Thread(() -> {
            s.run();
        });
        serverThread.start();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        Client cli = new Client(1324, "localhost", output);
        cli.displayMap();
        assertEquals("", bytes.toString());

        s.stop();
        serverThread.join();
        ss.close();
    }

}
