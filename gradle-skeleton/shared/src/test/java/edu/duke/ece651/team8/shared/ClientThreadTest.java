package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


import java.io.*;
import java.lang.reflect.Field;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class)
class ClientThreadTest {

    public Client createClient(int port, String host, OutputStream bytes, String inputData)throws IOException{
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);
        return  new Client(port, host, output, input);
    }

    @Test
    public void testRun() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        ServerSocket ss = new ServerSocket(1231);
        AbstractMapFactory factory = new V1MapFactory();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        Client cli = createClient(1231,"localhost", bytes, "1\n2\n3\n4\n5\n6\nM\n6\na1\na2\nD\n");

        Socket cliSocket = ss.accept();
        List<Socket> clis = new ArrayList<>();

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
                "0 units in a6 (next to: a5)\n"+
                "Please enter the units you would like to place in a1\nvalid\n\n" +
                "Please enter the units you would like to place in a3\nvalid\n\n" +
                "Please enter the units you would like to place in a5\nvalid\n\n" +
                "Please enter the units you would like to place in a1\nvalid\n\n" +
                "Please enter the units you would like to place in a3\nvalid\n\n" +
                "Placement phase is done!\n"+
                "You are the Green player, what would you like to do?\n"+
                "(M)ove\n"+
                "(A)ttack\n"+
                "(D)oneUnits number should be non_negative number\n"+
                "You are the Green player, what would you like to do?\n"+
                "(M)ove\n"+
                "(A)ttack\n"+
                "(D)onePlease enter the number of units to move:\n"+
                "Please enter the source territory:\n"+
                "Please enter the destination territory:\n"+
                "Please enter the destination territory:", actual);
    }

    @Test
    public void testHandlesIOExceptionInIssueOrder() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        AbstractMapFactory factory = new V1MapFactory();
        ServerSocket ss = new ServerSocket(1231);

        // Create mock objects
        BufferedReader mockReader = mock(BufferedReader.class);
        List<BufferedReader> r = new ArrayList<>();
        r.add(mockReader);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        Socket s = new Socket("localhost", 1231);
        Client cli = new Client(s, output, in);
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
        clientThread.issueOrders();

        cli.receive();
        cliOutput.println("M");
        String END_OF_TURN = "END_OF_TURN\n";
        cliOutput.print(END_OF_TURN);
        cliOutput.flush(); // flush the output buffer

        clientThread.interrupt();
        clientThread.join();

    }
    @Test
    public void testIssueOrdersAttack() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String END_OF_TURN = "END_OF_TURN";
        ServerSocket ss = new ServerSocket(1231);
        AbstractMapFactory factory = new V1MapFactory();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        Client cli = createClient(1231,"localhost", bytes, "A\n1\na1\nb1\nD\n");

        Socket cliSocket = ss.accept();
        List<Socket> clis = new ArrayList<Socket>();
        clis.add(cliSocket);
        ClientThread th = new ClientThread(clis, factory);
        // create a new thread and start it
        Thread thread = new Thread(() -> {
            th.issueOrders();
        });
        thread.start();

        cli.doOneOrder();
        thread.interrupt();
        thread.join();
        ss.close();
        String actual = bytes.toString().replaceAll("\\r\\n|\\r|\\n", "\n");

        assertEquals("You are the Green player, what would you like to do?\n"+
                "(M)ove\n"+
                "(A)ttack\n"+
                "(D)onePlease enter the number of units to attack:\n"+
                "Please enter the source territory:\n"+
                "Please enter the destination territory:\n"+
                "Please enter the destination territory:", actual);
    }


}