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
        Map m = new Game1Map();
        m.addTerritory(new BasicTerritory("Planto"));
        View mapView = new MapTextView(m);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        Client cli = new Client(1231, "localhost", output);

        Socket cliSocket = ss.accept();
        List<Socket> clis = new ArrayList<Socket>();
        List<String> color = new ArrayList<>();
        color.add("Red");
        clis.add(cliSocket);
        ClientThread th = new ClientThread(clis, color, mapView.displayMap(color));
        th.start();

        cli.run();

        th.interrupt();
        th.join();
        ss.close();
        String actual = bytes.toString().replaceAll("\\r\\n|\\r|\\n", "\n");
        assertEquals("Red\nRed Player:\n-------------\n0 units in Planto (next to: )\n", actual);

    }

    @Test
    public void testServerHandlesIOException() throws Exception {
        Map m = new Game1Map();
        m.addTerritory(new BasicTerritory("Planto"));
        View mapView = new MapTextView(m);

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
        clis.add(mockSocket);
        ClientThread clientThread = new ClientThread(clis, color, mapView.displayMap(color));
        doThrow(new IOException("Socket closed")).when(mockSocket).getOutputStream();
        clientThread.start();

        clientThread.interrupt();
        clientThread.join();

    }
}