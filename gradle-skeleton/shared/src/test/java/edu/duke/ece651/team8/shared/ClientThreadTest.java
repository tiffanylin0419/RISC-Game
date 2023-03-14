package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ClientThreadTest {
    @Test
    public void testRun() throws Exception {
        ServerSocket ss = new ServerSocket(1231);
        AbstractMapFactory factory = new V1MapFactory();
        Map m = factory.createMap(1);
        View mapView = new MapTextView();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        Client cli = new Client(1231, "localhost", output);

        Socket cliSocket = ss.accept();
        List<Socket> clis = new ArrayList<Socket>();
        List<String> color = new ArrayList<>();
        color.add("Green");
        ArrayList<Player> players=factory.createPlayers(1,m);

        clis.add(cliSocket);
        ClientThread th = new ClientThread(clis, color, mapView.displayMap(players));
        th.start();

        cli.run();

        th.interrupt();
        th.join();
        ss.close();
        String actual = bytes.toString().replaceAll("\\r\\n|\\r|\\n", "\n");
        assertEquals("Green\n" +
                "Green Player:\n" +
                "-------------\n" +
                "0 units in a1 (next to: a2)\n" +
                "0 units in a2 (next to: a1, a3)\n" +
                "0 units in a3 (next to: a2, a4)\n" +
                "0 units in a4 (next to: a3, a5)\n" +
                "0 units in a5 (next to: a4, a6)\n" +
                "0 units in a6 (next to: a5)\n", actual);
    }

    @Test
    public void testServerHandlesIOException() throws Exception {
        Map m = new Game1Map();
        m.addTerritory(new BasicTerritory("Planto"));
        View mapView = new MapTextView();

        // Create mock objects
        Socket mockSocket = mock(Socket.class);
        InputStream mockInputStream = mock(InputStream.class);
        OutputStream mockOutputStream = mock(OutputStream.class);
        // Set up mock socket
        when(mockSocket.getInputStream()).thenReturn(mockInputStream);
        when(mockSocket.getOutputStream()).thenReturn(mockOutputStream);

        // Create client thread with mock socket
        List<Socket> clis = new ArrayList<Socket>();
        List<String> color = new ArrayList<>();
        color.add("Red");

        ArrayList<Player> players=new ArrayList<>();
        players.add(new Player("Red"));

        clis.add(mockSocket);
        ClientThread clientThread = new ClientThread(clis, color, mapView.displayMap(players));
        doThrow(new IOException("Socket closed")).when(mockSocket).getOutputStream();
        clientThread.start();

        clientThread.interrupt();
        clientThread.join();

    }
}