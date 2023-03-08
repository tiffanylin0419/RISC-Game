package edu.duke.team8.riskgame;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ServerTest {
    @Test
    public void testConstructor() throws IOException {
        Map m = new Game1Map();
        m.addTerritory(new BasicTerritory("Planto"));

        Server s = new Server(1236, m, 4);
        assertEquals(1236, s.getPort());
    }
    @Test()
    public void testIOException() throws Exception {
        Map m = new Game1Map();
        m.addTerritory(new BasicTerritory("Planto"));
        View mapView = new MapTextView(m);

        // Create mock objects
        ServerSocket mockSs = mock(ServerSocket.class);
        Socket mockSocket = mock(Socket.class);
        // Set up mock socket
        when(mockSs.accept()).thenReturn(mockSocket);

        // Create client thread with mock socket
        Server s = new Server(mockSs, m, 1);
        doThrow(new IOException("Socket closed")).when(mockSs).accept();
        s.run();
        s.stop();
    }
    private void checkClientHelper(String expected) throws Exception {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        Client cli = new Client(1216, "localhost", output);
        cli.run();
        String actual = bytes.toString().replaceAll("\\r\\n|\\r|\\n", "\n");
        assertEquals(expected, actual);
    }
    @Test
    public void testRun() throws Exception {

        ServerSocket ss = new ServerSocket(1216);
        Map m = new Game1Map();
        m.addTerritory(new BasicTerritory("Planto"));

        Server s = new Server(ss, m, 4);
        Thread serverThread = new Thread(() -> {
            s.run();
        });
        serverThread.start();

        checkClientHelper("Green\n0 units in Planto\n");
        checkClientHelper("Red\n0 units in Planto\n");
        checkClientHelper("Blue\n0 units in Planto\n");
        checkClientHelper("Yellow\n0 units in Planto\n");

        s.stop();
        serverThread.join();
        ss.close();

    }
    @Test
    public void testHasSocket() throws Exception {

        ServerSocket ss = new ServerSocket(1219);
        Map m = new Game1Map();
        m.addTerritory(new BasicTerritory("Planto"));

        Server s = new Server(ss, m, 1);
        Thread serverThread = new Thread(() -> {
            s.run();
        });
        serverThread.start();

        ByteArrayOutputStream bytes1 = new ByteArrayOutputStream();
        PrintStream output1 = new PrintStream(bytes1, true);
        Socket cliSocket = new Socket("localhost", 1219);
        Client cli = new Client(cliSocket, output1);
        cli.run();

        s.stop();
        serverThread.join();
        ss.close();
        String actual = bytes1.toString().replaceAll("\\r\\n|\\r|\\n", "\n");
        assertEquals("Green\n0 units in Planto\n", actual);
    }

}
