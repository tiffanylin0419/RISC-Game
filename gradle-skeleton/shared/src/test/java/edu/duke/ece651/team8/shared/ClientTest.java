package edu.duke.ece651.team8.shared;

import edu.duke.ece651.team8.shared.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {
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
        assertThrows(NumberFormatException.class,()->cli.isNonNegativeInt("svin"));
        assertFalse(cli.isNonNegativeInt("-10"));
        assertTrue(cli.isNonNegativeInt("123"));
    }
    public Client createClient(int port, String host, OutputStream bytes, String inputData)throws IOException{
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);
        return  new Client(port, host, output, input);
    }
    @Test
    public void testIOExceptionInRun() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        ServerSocket ss = new ServerSocket(1231);
        AbstractMapFactory factory = new V1MapFactory();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        Client cli = createClient(1231,"localhost", bytes, "-1\n70\n1\n2\n3\n4\n5\n6\nM\n-6\n6\na1\na2\nD\n");

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
                "Please enter the units you would like to place in a1\n"+
                "Units number should be non_negative number\n"+"Please input a valid placement!\n"+
                "Please enter the units you would like to place in a1\ninvalid\n\n" +
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
                "Units number should be non_negative number\n"+
                "Please input a valid unit number!\n"+
                "Please enter the number of units to move:\n"+
                "Please enter the source territory:\n"+
                "Please enter the destination territory:\n"+
                "Please enter the destination territory:", actual);
    }
    @Disabled
    @Test
    public void testRun() throws Exception {
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
        Client cli = new Client(client, output,in);
        cli.run();
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
        cli.display();
        String actual = bytes.toString().replaceAll("\\r\\n|\\r|\\n", "\n");
        assertEquals("\n", actual);

        s.stop();
        serverThread.join();
        ss.close();
    }
    @Disabled
    @Test
    public void testDisplayMap() throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        AbstractMapFactory factory = new V1MapFactory();
        ServerSocket ss = new ServerSocket(1324);

        Server s = new Server(ss, 1, factory);
        Thread serverThread = new Thread(() -> {
            s.run();
        });
        serverThread.start();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
        Client cli = new Client(1324, "localhost", output, in);
        cli.displayMap();
        assertEquals("", bytes.toString());

        s.stop();
        serverThread.join();
        ss.close();
    }

}
