package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.*;
import java.lang.reflect.Field;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


//@ExtendWith(MockitoExtension.class)
class ClientThreadTest {
    @Test
    public void testRun() throws Exception {
        ServerSocket ss = new ServerSocket(1231);
        AbstractMapFactory factory = new V1MapFactory();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        Client cli = new Client(1231, "localhost", output, System.in);

        Socket cliSocket = ss.accept();
        List<Socket> clis = new ArrayList<Socket>();

        clis.add(cliSocket);
        ClientThread th = new ClientThread(clis, factory);
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
                "0 units in a6 (next to: a5)", actual);
    }

    @Test
    public void testServerHandlesIOException() throws Exception {
        AbstractMapFactory factory = new V1MapFactory();

        // Create mock objects
        Socket mockSocket = mock(Socket.class);
        InputStream mockInputStream = mock(InputStream.class);
        OutputStream mockOutputStream = mock(OutputStream.class);
        // Set up mock socket
        when(mockSocket.getInputStream()).thenReturn(mockInputStream);
        when(mockSocket.getOutputStream()).thenReturn(mockOutputStream);

        // Create client thread with mock socket
        List<Socket> clis = new ArrayList<Socket>();

        ArrayList<Player> players=new ArrayList<>();
        players.add(new Player("Red"));

        clis.add(mockSocket);
        ClientThread clientThread = new ClientThread(clis, factory);
        doThrow(new IOException("Socket closed")).when(mockSocket).getOutputStream();
        clientThread.start();

        clientThread.interrupt();
        clientThread.join();

    }
    @Test
    public void testHandlesIOExceptionInRun() throws Exception {
        AbstractMapFactory factory = new V1MapFactory();
        ServerSocket ss = new ServerSocket(1231);

        // Create mock objects
        BufferedReader mockReader = mock(BufferedReader.class);
        List<BufferedReader> r = new ArrayList<>();
        r.add(mockReader);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        Socket s = new Socket("localhost", 1231);
        Client cli = new Client(s, output, System.in);
        PrintWriter cliOutput = new PrintWriter(s.getOutputStream(), true);

        Socket cliSocket = ss.accept();
        List<Socket> clis = new ArrayList<Socket>();
        clis.add(cliSocket);

        ClientThread clientThread = new ClientThread(clis, factory);
        // Set the mock BufferedReader object on the clientThread
        when(mockReader.readLine()).thenThrow(new IOException("Socket closed"));
        Field readerField = ClientThread.class.getDeclaredField("readers");
        readerField.setAccessible(true);
        readerField.set(clientThread, r);
        clientThread.start();

        cli.receiveColor();
        cli.receiveMapInfo();
        cli.receive();
        cliOutput.println("M");
        String END_OF_TURN = "END_OF_TURN\n";
        cliOutput.print(END_OF_TURN);
        cliOutput.flush(); // flush the output buffer

        clientThread.interrupt();
        clientThread.join();

    }
    @Test
    public void testIssueOrders() throws Exception {
        String END_OF_TURN = "END_OF_TURN\n";
        ServerSocket ss = new ServerSocket(1231);
        AbstractMapFactory factory = new V1MapFactory();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        Socket s = new Socket("localhost", 1231);
        Client cli = new Client(s, output, System.in);
        PrintWriter cliOutput = new PrintWriter(s.getOutputStream(), true);

        Socket cliSocket = ss.accept();
        List<Socket> clis = new ArrayList<Socket>();
        clis.add(cliSocket);
        ClientThread th = new ClientThread(clis, factory);
        th.start();

        cli.receiveColor();
        cli.receiveMapInfo();
        cli.receive();
        cliOutput.println("M");
        cliOutput.print(END_OF_TURN);
        cliOutput.flush(); // flush the output buffer

        cli.receive();
        cliOutput.println("3");
        cliOutput.print(END_OF_TURN);
        cliOutput.flush(); // flush the output buffer

        cli.receive();
        cliOutput.println("a1");
        cliOutput.print(END_OF_TURN);
        cliOutput.flush(); // flush the output buffer

        cli.receive();
        cliOutput.println("a2");
        cliOutput.print(END_OF_TURN);
        cliOutput.flush(); // flush the output buffer

        cliOutput.println("M");
        cliOutput.print(END_OF_TURN);
        cliOutput.flush(); // flush the output buffer

        cli.receive();
        cliOutput.println("3");
        cliOutput.print(END_OF_TURN);
        cliOutput.flush(); // flush the output buffer

        cli.receive();
        cliOutput.println("a1");
        cliOutput.print(END_OF_TURN);
        cliOutput.flush(); // flush the output buffer

        cli.receive();
        cliOutput.println("a2");
        cliOutput.print(END_OF_TURN);
        cliOutput.flush(); // flush the output buffer

        cliOutput.println("commit");
        cliOutput.print(END_OF_TURN);
        cliOutput.flush(); // flush the output buffer

        th.interrupt();
        th.join();

        cliOutput.close();
        ss.close();
//        String actual = bytes.toString().replaceAll("\\r\\n|\\r|\\n", "\n");
//        assertEquals("Green\n" +
//                "Green Player:\n" +
//                "-------------\n" +
//                "0 units in a1 (next to: a2)\n" +
//                "0 units in a2 (next to: a1, a3)\n" +
//                "0 units in a3 (next to: a2, a4)\n" +
//                "0 units in a4 (next to: a3, a5)\n" +
//                "0 units in a5 (next to: a4, a6)\n" +
//                "0 units in a6 (next to: a5)", actual);
    }
}