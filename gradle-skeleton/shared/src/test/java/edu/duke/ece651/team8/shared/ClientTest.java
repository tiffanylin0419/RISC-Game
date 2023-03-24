package edu.duke.ece651.team8.shared;

import java.io.*;
import java.lang.reflect.Field;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {
    public Client createClient(BufferedReader mockRB, Socket s,OutputStream bytes,InputStream inputStream,PrintWriter output, String inputData)throws IOException{
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream out = new PrintStream(bytes, true);
        return  new Client(s, inputStream, mockRB, out, input, output);
    }
    @Test
    public void testConstructor() throws Exception {
        AbstractMapFactory factory = new V1MapFactory();
        ServerSocket ss = new ServerSocket(1237);

        Server s = new Server(ss, 1, factory);
        Thread serverThread = new Thread(() -> {
            s.run();
        });
        serverThread.start();

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Client c = new Client(1237, "localhost",in);
        s.stop();
        serverThread.join();
        ss.close();
    }

    @Test()
    public void testIOException() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        AbstractMapFactory factory = new V1MapFactory();
        ServerSocket ss = new ServerSocket(1239);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);

        Server s = new Server(ss, 1, factory);
        Thread serverThread = new Thread(() -> {
            s.run();
        });
        serverThread.start();

        Socket cl_s = new Socket("localhost", 1239);
        Client cli = new Client(cl_s, output,in);
        cl_s.close();
        cli.run();
        s.stop();
        serverThread.join();
        ss.close();
        String actual = bytes.toString().replaceAll("\\r\\n|\\r|\\n", "\n");
        assertEquals("Socket closed\n", actual);
    }

    @Test
    public void testIisNonNegativeInt()throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        ServerSocket ss = new ServerSocket(1244);
        AbstractMapFactory factory = new V1MapFactory();

        Server s = new Server(ss, 1, factory);
        Thread serverThread = new Thread(() -> {
            s.run();
        });
        serverThread.start();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        Socket client = new Socket("localhost", 1244);
        Client cli = new Client(client, output, in);
        assertThrows(NumberFormatException.class,()->cli.isPositiveInt("svin"));
        assertFalse(cli.isPositiveInt("-10"));
        assertTrue(cli.isPositiveInt("123"));
    }
//
//    @Test
//    public void testRun() throws Exception {
//        Client mockClient= mock(Client.class);
//        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//        ServerSocket ss = new ServerSocket(1831);
//        AbstractMapFactory factory = new V1MapFactory();
//
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        BufferedReader input = new BufferedReader(new StringReader("1\n2\n3\n4\n5\n6\nM\n1\na1\na2\nD\n"));
//        PrintStream out = new PrintStream(bytes, true);
//        Client cli = new Client(1831, "localhost", out, input);
//        when(mockWinner.equals("no winner")).thenReturn(false);
//        Field winnerField = Client.class.getDeclaredField("winner");
//        winnerField.setAccessible(true);
//        winnerField.set(cli, mockWinner);
//
//        Socket cliSocket = ss.accept();
//        List<Socket> clis = new ArrayList<>();
//        clis.add(cliSocket);
//        GameThread th = new GameThread(clis, factory);
//        th.start();
//
//        cli.run();
//
//        th.interrupt();
//        th.join();
//        ss.close();
//        String actual = bytes.toString().replaceAll("\\r\\n|\\r|\\n", "\n");
//
//        assertEquals("", actual);
//
//    }
    @Disabled
    @Test
    public void testDisplay() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        AbstractMapFactory factory = new V1MapFactory();
        ServerSocket ss = new ServerSocket(1334);

        Server s = new Server(ss, 1,factory);
        Thread serverThread = new Thread(() -> {
            s.run();
        });
        serverThread.start();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        Client cli = new Client(1334, "localhost", output, in);
        cli.displayColor();
        cli.displayMap();
        String actual = bytes.toString().replaceAll("\\r\\n|\\r|\\n", "\n");
        assertEquals("\n", actual);

        s.stop();
        serverThread.join();
        ss.close();
    }

    @Test
    public void testDisplayMap() throws Exception {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes, true);
        PrintWriter output = mock(PrintWriter.class);
        Socket mockSocket = mock(Socket.class);
        InputStream inputStream = mock(InputStream.class);
        BufferedReader mockServerBuffer = mock(BufferedReader.class);
        Client client = createClient(mockServerBuffer,mockSocket,out,inputStream,output,"");
        when(mockServerBuffer.readLine()).thenReturn("abc").thenReturn("123").thenReturn("").thenReturn(client.END_OF_TURN);
        client.mapInfo = "abc\n123";
        client.displayMap();
        String actual = bytes.toString();
        assertEquals("abc\n123\n", actual);
    }


//    public Client createClient(BufferedReader mockRB, Socket s,OutputStream bytes, PrintWriter output, String inputData)throws IOException{
//        BufferedReader input = new BufferedReader(new StringReader(inputData));
//        PrintStream out = new PrintStream(bytes, true);
//        return  new Client(s, null, mockRB, out, input, output);
//    }
    @Test
    public void testReceive()throws IOException{
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes, true);
        PrintWriter output = mock(PrintWriter.class);
        Socket mockSocket = mock(Socket.class);
        InputStream inputStream = mock(InputStream.class);
        BufferedReader mockServerBuffer = mock(BufferedReader.class);
        Client client = createClient(mockServerBuffer,mockSocket,out,inputStream,output,"");

        when(mockServerBuffer.readLine()).thenReturn("abc").thenReturn("123").thenReturn("").thenReturn(client.END_OF_TURN);
        client.receive();
        assertEquals(client.buffer,"abc\n123\n");
    }

    @Test
    public void testReceiveCombatOutcome()throws IOException{
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes, true);
        PrintWriter output = mock(PrintWriter.class);
        Socket mockSocket = mock(Socket.class);
        InputStream inputStream = mock(InputStream.class);
        BufferedReader mockServerBuffer = mock(BufferedReader.class);
        Client client = createClient(mockServerBuffer,mockSocket,out,inputStream,output,"");

        when(mockServerBuffer.readLine()).thenReturn("abc").thenReturn("123").thenReturn("").thenReturn(client.END_OF_TURN);
        client.receiveCombatOutcome();
        assertEquals(client.combatOutcome,"abc\n123\n");
        client.displayCombatOutcome();
        String actual = bytes.toString();
        assertEquals("abc\n123\n\n", actual);

    }
    @Test
    public void testReceiveWinner()throws IOException{
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes, true);
        PrintWriter output = mock(PrintWriter.class);
        Socket mockSocket = mock(Socket.class);
        InputStream inputStream = mock(InputStream.class);
        BufferedReader mockServerBuffer = mock(BufferedReader.class);
        Client client = createClient(mockServerBuffer,mockSocket,out,inputStream,output,"");

        when(mockServerBuffer.readLine()).thenReturn("abc").thenReturn("123").thenReturn("").thenReturn(client.END_OF_TURN);
        client.receiveWinner();
        assertEquals(client.winner,"abc\n123\n");
    }

    @Test
    public void testReceiveLoseStatus()throws IOException{
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes, true);
        PrintWriter output = mock(PrintWriter.class);
        Socket mockSocket = mock(Socket.class);
        InputStream inputStream = mock(InputStream.class);
        BufferedReader mockServerBuffer = mock(BufferedReader.class);
        Client client = createClient(mockServerBuffer,mockSocket,out,inputStream,output,"A");

        when(mockServerBuffer.readLine()).thenReturn("continue").thenReturn(client.END_OF_TURN).thenReturn("lose").thenReturn(client.END_OF_TURN);
        client.receiveLoseStatus();
        assertEquals(client.isDefeated,false);
        client.receiveLoseStatus();
        assertEquals(client.isDefeated,true);
    }
    @Test
    public void testTryChooseOneAction()throws IOException{
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes, true);
        PrintWriter output = mock(PrintWriter.class);
        Socket mockSocket = mock(Socket.class);
        InputStream inputStream = mock(InputStream.class);
        BufferedReader mockServerBuffer = mock(BufferedReader.class);
        Client client = createClient(mockServerBuffer,mockSocket,out,inputStream,output,"A");

        client.tryChooseOneAction("What would you like to do?\n(M)ove\n(A)ttack\n(D)one",client.input);
        String actual = bytes.toString();
        assertEquals("What would you like to do?\n(M)ove\n(A)ttack\n(D)one", actual);
        verify(output).println("A");
        verify(output).println(client.END_OF_TURN);
        verify(output).flush();
    }

    @Test
    public void testIsOver()throws IOException{
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes, true);
        PrintWriter output = mock(PrintWriter.class);
        Socket mockSocket = mock(Socket.class);
        InputStream inputStream = mock(InputStream.class);
        BufferedReader mockServerBuffer = mock(BufferedReader.class);
        Client client = createClient(mockServerBuffer,mockSocket,out,inputStream,output,"A");
        assertEquals(client.isOver(),false);
    }
}
