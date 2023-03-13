package edu.duke.ece651.team8.shared;

import edu.duke.ece651.team8.shared.*;
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
        AbstractMapFactory factory = new V1MapFactory();
        Map m = factory.createMap(1);

        Server s = new Server(ss, m, 1);
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
        assertEquals("Green\n" +
                "Green Player:\n" +
                "-------------\n" +
                "0 units in a1 (next to: a2)\n" +
                "0 units in a2 (next to: a3)\n" +
                "0 units in a3 (next to: a4)\n" +
                "0 units in a4 (next to: a5)\n" +
                "0 units in a5 (next to: a6)\n" +
                "0 units in a6 (next to: )\n", actual);

        s.stop();
        serverThread.join();
        ss.close();

    }
//    @Test
//    public void testReceive() throws Exception {
//        ServerSocket ss = new ServerSocket(1234);
//        Map m = new Game1Map();
//        m.addTerritory(new BasicTerritory("Planto"));
//        m.addTerritory(new BasicTerritory("Dova"));
//        m.addTerritory(new BasicTerritory("Aova"));
//
//        Territory t = new BasicTerritory("Grand");
//        Player p = new TextPlayer("Green");
//        Unit u = new BasicUnit(2, p);
//        t.moveIn(u);
//        m.addTerritory(t);
//
//        Server s = new Server(ss, m, 1);
//        Thread serverThread = new Thread(() -> {
//            s.run();
//        });
//        serverThread.start();
//
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        PrintStream output = new PrintStream(bytes, true);
//        Client cli = new Client(1234, "localhost", output);
//        cli.receive();
//        cli.display();
//        serverThread.interrupt();
//        s.stop();
//        serverThread.join();
//        ss.close();
//        String actual = bytes.toString().replaceAll("\\r\\n|\\r|\\n", "\n");
//        assertEquals("Green\nGreen Player:\n-------------\n0 units in Planto (next to: )\n0 units in Dova (next to: )\n" +
//                "0 units in Aova (next to: )\n2 units in Grand (next to: )\n", actual);
//
//    }
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
