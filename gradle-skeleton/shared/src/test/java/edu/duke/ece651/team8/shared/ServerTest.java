package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ServerTest {
    @Test
    public void testSignupAndLogin() throws Exception {
        AbstractMapFactory factory = new V1MapFactory();

        // Create mock objects
        ServerSocket mockSs = mock(ServerSocket.class);
        Socket mockSocket = mock(Socket.class);
        BufferedReader reader = mock(BufferedReader.class);
        PrintWriter writer = mock(PrintWriter.class);
        // Set up mock socket
        when(mockSs.accept()).thenReturn(mockSocket);
        when(reader.readLine()).thenReturn("S").thenReturn("END_OF_TURN").thenReturn("qwe").thenReturn("END_OF_TURN").thenReturn("123").
                thenReturn("END_OF_TURN").thenReturn("L").thenReturn("END_OF_TURN").thenReturn("qwe").thenReturn("END_OF_TURN").
                thenReturn("123").thenReturn("END_OF_TURN");
        Server server = new Server(mockSs, factory);
        Thread th = new Thread(() -> {
            try {
                server.processLoginAndSignup(writer, reader);
                server.processLoginAndSignup(writer, reader);
            } catch(Exception e) {}
        });
        th.start();
        sleep(2000);
        server.stop();
        th.join();
    }
    @Test
    public void testFindMatch() throws Exception {
        AbstractMapFactory factory = new V2MapFactory();

        // Create mock objects
        ServerSocket mockSs = mock(ServerSocket.class);
        Socket mockSocket = mock(Socket.class);
        BufferedReader reader = mock(BufferedReader.class);
        PrintWriter writer = mock(PrintWriter.class);
        PlayerAccount acc = new PlayerAccount(writer, reader, "qwe", "123");
        GameThread game = new GameThread(3, factory, 0);
        acc.addJoinGame(game, 0);
        // Set up mock socket
        when(mockSs.accept()).thenReturn(mockSocket);
        when(reader.readLine()).thenReturn("N").thenReturn("END_OF_TURN").thenReturn("2").thenReturn("END_OF_TURN")
                .thenReturn("Y").thenReturn("END_OF_TURN").thenReturn("0").thenReturn("END_OF_TURN");
        Server server = new Server(mockSs, factory);
        Thread th = new Thread(() -> {
            try {
                server.findMatch(acc);
                server.findMatch(acc);
            } catch(Exception e) {}
        });
        th.start();
        sleep(2000);
        server.stop();
        th.join();
    }
//    @Test
//    public void testConstructor() throws IOException {
//
//
//        AbstractMapFactory factory = new V1MapFactory();
//        Server s = new Server(1236, 4, factory);
//        assertEquals(1236, s.getPort());
//    }
//    @Test()
//    public void testIOException() throws Exception {
//        AbstractMapFactory factory = new V1MapFactory();
//
//        // Create mock objects
//        ServerSocket mockSs = mock(ServerSocket.class);
//        Socket mockSocket = mock(Socket.class);
//        // Set up mock socket
//        when(mockSs.accept()).thenReturn(mockSocket);
//
//        ArrayList<Player> players=new ArrayList<>();
//        players.addAmount(new Player("Green"));
//
//        // Create client thread with mock socket
//        Server s = new Server(mockSs, 1, factory);
//        doThrow(new IOException("Socket closed")).when(mockSs).accept();
//        Thread serverThread = new Thread(() -> {
//            s.run();
//        });
//        serverThread.start();
//        Thread.sleep(100);
//        s.stop();
//        serverThread.interrupt();
//        serverThread.join();
//    }
//    private void checkClientHelper(ByteArrayOutputStream bytes, Client cli, String expected) throws Exception {
//        cli.receiveColor();
//        cli.receiveMap();
//        cli.displayColor();
//        cli.displayMap();
//        String actual = bytes.toString().replaceAll("\\r\\n|\\r|\\n", "\n");
//        assertEquals(expected, actual);
//    }
//    @Test
//    public void testRun() throws Exception {
//        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//
//        ServerSocket ss = new ServerSocket(1216);
//
//        AbstractMapFactory factory = new V1MapFactory();
//
//
//        Server s = new Server(ss, 2, factory);
//        Thread serverThread = new Thread(() -> {
//            s.run();
//        });
//        serverThread.start();
//
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        PrintStream output = new PrintStream(bytes, true);
//        Client cli = new Client(1216, "localhost", output, in);
//
//        ByteArrayOutputStream bytes1 = new ByteArrayOutputStream();
//        PrintStream output1 = new PrintStream(bytes1, true);
//        Client cli1 = new Client(1216, "localhost", output1, in);
//
//        checkClientHelper(bytes, cli, "Green\n" +
//                "Green Player:\n" +
//                "-------------\n" +
//                "0 units in a1 (next to: b1, a2)\n" +
//                "0 units in a2 (next to: a1, b2, a3)\n" +
//                "0 units in a3 (next to: a2, b3, a4)\n" +
//                "0 units in a4 (next to: a3, b4, a5)\n" +
//                "0 units in a5 (next to: a4, b5, a6)\n" +
//                "0 units in a6 (next to: a5, b6)\n" +
//                "Red Player:\n" +
//                "-------------\n" +
//                "0 units in b1 (next to: a1, b2)\n" +
//                "0 units in b2 (next to: a2, b1, b3)\n" +
//                "0 units in b3 (next to: a3, b2, b4)\n" +
//                "0 units in b4 (next to: a4, b3, b5)\n" +
//                "0 units in b5 (next to: a5, b4, b6)\n" +
//                "0 units in b6 (next to: a6, b5)\n");
//        checkClientHelper(bytes1, cli1, "Red\n" +
//                "Green Player:\n" +
//                "-------------\n" +
//                "0 units in a1 (next to: b1, a2)\n" +
//                "0 units in a2 (next to: a1, b2, a3)\n" +
//                "0 units in a3 (next to: a2, b3, a4)\n" +
//                "0 units in a4 (next to: a3, b4, a5)\n" +
//                "0 units in a5 (next to: a4, b5, a6)\n" +
//                "0 units in a6 (next to: a5, b6)\n" +
//                "Red Player:\n" +
//                "-------------\n" +
//                "0 units in b1 (next to: a1, b2)\n" +
//                "0 units in b2 (next to: a2, b1, b3)\n" +
//                "0 units in b3 (next to: a3, b2, b4)\n" +
//                "0 units in b4 (next to: a4, b3, b5)\n" +
//                "0 units in b5 (next to: a5, b4, b6)\n" +
//                "0 units in b6 (next to: a6, b5)\n");
//
//        s.stop();
//        serverThread.join();
//        ss.close();
//
//    }
}
