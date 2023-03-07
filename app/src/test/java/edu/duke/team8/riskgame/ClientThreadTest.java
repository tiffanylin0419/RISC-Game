package edu.duke.team8.riskgame;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
        ClientThread th = new ClientThread(cliSocket, "Red", mapView.displayMap());
        th.start();

        cli.run();

        th.stopThread();
        th.interrupt();
        th.join();
        ss.close();
        String actual = bytes.toString().replaceAll("\\r\\n|\\r|\\n", "\n");
        assertEquals("Red\n0 units in Planto\n", actual);

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
        ClientThread clientThread = new ClientThread(mockSocket, "Red", mapView.displayMap());
        doThrow(new IOException("Socket closed")).when(mockSocket).close();
        clientThread.start();

        clientThread.stopThread();
        clientThread.interrupt();
        clientThread.join();

    }
}