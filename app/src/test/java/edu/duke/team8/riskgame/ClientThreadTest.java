package edu.duke.team8.riskgame;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class ClientThreadTest {
    @Test
    public void testRun() throws Exception {
        ServerSocket ss = new ServerSocket(1231);
        Map m = new Game1Map();
        m.addTerritory(new BasicTerritory("Planto"));
        View mapView = new MapTextView(m);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        Client cli = new Client(1231, "localhost", output);

        Socket cliSocket = ss.accept();
        ClientThread th = new ClientThread(cliSocket, "Red", mapView.displayMap());
        th.start();

        cli.run();

        th.interrupt();
        th.join();
        ss.close();
        assertEquals("Red\n0 units in Planto\n", bytes.toString());

    }
    @Test
    public void testGetSocket() throws Exception{
        ServerSocket ss = new ServerSocket(1231);
        Map m = new Game1Map();
        m.addTerritory(new BasicTerritory("Planto"));
        View mapView = new MapTextView(m);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        Client cli = new Client(1231, "localhost", output);

        Socket cliSocket = ss.accept();
        ClientThread th = new ClientThread(cliSocket, "Red", mapView.displayMap());
        assertEquals(cliSocket, th.getSocket());
    }

}