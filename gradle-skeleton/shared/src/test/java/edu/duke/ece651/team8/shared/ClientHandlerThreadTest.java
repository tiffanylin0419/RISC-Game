package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Field;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ClientHandlerThreadTest {

    @Test
    public void testSendInitialCommit() throws Exception {
        PrintWriter output = mock(PrintWriter.class);
        InputStream inputStream = mock(InputStream.class);
        BufferedReader reader = mock(BufferedReader.class);

        PrintWriter output1 = mock(PrintWriter.class);
        InputStream inputStream1 = mock(InputStream.class);
        BufferedReader reader1 = mock(BufferedReader.class);

        GameThread th = new GameThread(2, new V2MapFactory(), 0);
        th.start();
        PlayerAccount account = new PlayerAccount(output, reader, "asd", "1");
        PlayerAccount account1 = new PlayerAccount(output1, reader1, "as", "2");


        ClientHandlerThread handler = th.join(account);
        ClientHandlerThread handler1 = th.join(account1);
        th.shutDown();

    }
    @Test
    public void testReconnect() throws Exception {
        PrintWriter output = mock(PrintWriter.class);
        BufferedReader reader = mock(BufferedReader.class);

        PrintWriter output1 = mock(PrintWriter.class);
        BufferedReader reader1 = mock(BufferedReader.class);

        GameThread th = new GameThread(2, new V2MapFactory(), 0);
        th.start();
        PlayerAccount account = new PlayerAccount(output, reader, "asd", "1");
        PlayerAccount account1 = new PlayerAccount(output1, reader1, "as", "2");

        when(reader.readLine()).thenReturn("3").thenReturn("END_OF_TURN").thenReturn("3").thenReturn("END_OF_TURN").thenReturn("3").
                thenReturn("END_OF_TURN").thenReturn("3").thenReturn("END_OF_TURN").thenReturn("3").thenReturn("END_OF_TURN").
                thenReturn("M").thenReturn("END_OF_TURN").thenReturn("2").thenReturn("END_OF_TURN").
                thenReturn("a1").thenReturn("END_OF_TURN").thenReturn("a2").thenReturn("END_OF_TURN").
                thenReturn("A").thenReturn("END_OF_TURN").thenReturn("2").thenReturn("END_OF_TURN").
                thenReturn("a1").thenReturn("END_OF_TURN").thenReturn("b1").thenReturn("END_OF_TURN").
                thenReturn("D").thenReturn("END_OF_TURN");

        when(reader1.readLine()).thenReturn("3").thenReturn("END_OF_TURN").thenReturn("3").thenReturn("END_OF_TURN").thenReturn("3").
                thenReturn("END_OF_TURN").thenReturn("3").thenReturn("END_OF_TURN").thenReturn("3").thenReturn("END_OF_TURN").
                thenReturn("R").thenReturn("END_OF_TURN").
                thenReturn("U").thenReturn("END_OF_TURN").thenReturn("a1").thenReturn("END_OF_TURN").thenReturn("2").thenReturn("END_OF_TURN").
                thenReturn("0").thenReturn("END_OF_TURN").thenReturn("1").thenReturn("END_OF_TURN").
                thenReturn("D").thenReturn("END_OF_TURN");


        ClientHandlerThread handler = th.join(account);
        ClientHandlerThread handler1 = th.join(account1);
        sleep(1000);
        th.shutDown();

    }
    @Test
    public void testCheckUnitNumValid() {
        PrintWriter output = mock(PrintWriter.class);
        BufferedReader reader = mock(BufferedReader.class);

        GameThread th = new GameThread(2, new V2MapFactory(), 0);
        th.start();
        PlayerAccount account = new PlayerAccount(output, reader, "asd", "1");

        ClientHandlerThread handler = th.join(account);
        handler.buffer = "1";
        assertEquals(true, handler.checkUnitNumValid(3, 3, 4));
    }
//    @Test
//    public void testIOExceptionInRun() throws Exception {
//        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//        ServerSocket ss = new ServerSocket(1531);
//        AbstractMapFactory factory = new V1MapFactory();
//
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        Client cli = createClient(1531,"localhost", bytes, "-1\n70\n0\n2\n3\n4\n5\n6\nM\n-6\n6\na5\na2\nD\n");
//
//        Socket cliSocket = ss.accept();
//        List<Socket> clis = new ArrayList<>();
//
//        clis.addAmount(cliSocket);
//        GameThread th = new GameThread(clis, factory);
//        th.start();
//
//        ClientHandlerThread cliTh =  th.getClients().get(0);
//        doClientAction(cli, cliTh);
//
//        cliTh.interrupt();
//        th.interrupt();
//        th.join();
//        ss.close();
//        String actual = bytes.toString().replaceAll("\\r\\n|\\r|\\n", "\n");
//
//        assertEquals("Green\n" +
//                "Green Player:\n" +
//                "-------------\n" +
//                "0 units in a1 (next to: a2)\n" +
//                "0 units in a2 (next to: a1, a3)\n" +
//                "0 units in a3 (next to: a2, a4)\n" +
//                "0 units in a4 (next to: a3, a5)\n" +
//                "0 units in a5 (next to: a4, a6)\n" +
//                "0 units in a6 (next to: a5)\n" +
//                "Please enter the units you would like to place in a1 (36 units)\n" +
//                "Units number should be non_negative number\n" +
//                "Please input a valid placement!\n" +
//                "Please enter the units you would like to place in a1 (36 units)\n" +
//                "invalid\n" +
//                "\n" +
//                "Please enter the units you would like to place in a1 (36 units)\n" +
//                "Units number should be non_negative number\n" +
//                "Please input a valid placement!\n" +
//                "Please enter the units you would like to place in a1 (36 units)\n" +
//                "valid\n" +
//                "\n" +
//                "Please enter the units you would like to place in a2 (34 units)\n" +
//                "valid\n" +
//                "\n" +
//                "Please enter the units you would like to place in a3 (31 units)\n" +
//                "valid\n" +
//                "\n" +
//                "Please enter the units you would like to place in a4 (27 units)\n" +
//                "valid\n" +
//                "\n" +
//                "Please enter the units you would like to place in a5 (22 units)\n" +
//                "valid\n" +
//                "\n" +
//                "Placement phase is done!\n" +
//                "\n" +
//                "Green Player:\n" +
//                "-------------\n" +
//                "2 units in a1 (next to: a2)\n" +
//                "3 units in a2 (next to: a1, a3)\n" +
//                "4 units in a3 (next to: a2, a4)\n" +
//                "5 units in a4 (next to: a3, a5)\n" +
//                "6 units in a5 (next to: a4, a6)\n" +
//                "16 units in a6 (next to: a5)\n" +
//                "You are the Green player, what would you like to do?\n" +
//                "(M)ove\n" +
//                "(A)ttack\n" +
//                "(D)one\n"+
//                "Please enter the number of units to move:\n" +
//                "Units number should be non_negative number\n" +
//                "Please enter the number of units to move:\n" +
//                "Please enter the source territory:\n" +
//                "Please enter the destination territory:\n" +
//                "You are the Green player, what would you like to do?\n" +
//                "(M)ove\n" +
//                "(A)ttack\n" +
//                "(D)one\n" +
//                "\n"+
//                "Green Player:\n" +
//                "-------------\n" +
//                "3 units in a1 (next to: a2)\n" +
//                "10 units in a2 (next to: a1, a3)\n" +
//                "5 units in a3 (next to: a2, a4)\n" +
//                "6 units in a4 (next to: a3, a5)\n" +
//                "1 units in a5 (next to: a4, a6)\n" +
//                "17 units in a6 (next to: a5)\nCongratulations! You win!\n", actual);
//    }
//
//    @Test
//    public void testHasWinner() throws Exception{
//        Player mockPlayer= mock(Player.class);
//        Player mockPlayer2= mock(Player.class);
//        Map mockMap= mock(Map.class);
//        List<Player> p = new ArrayList<>();
//        p.addAmount(mockPlayer);
//        p.addAmount(mockPlayer2);
//
//        AbstractMapFactory factory = new V1MapFactory();
//        ServerSocket ss = new ServerSocket(1291);
//
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        Client cli = createClient(1291,"localhost", bytes, "");
//        ByteArrayOutputStream bytes1 = new ByteArrayOutputStream();
//        Client cli1 = createClient(1291,"localhost", bytes1, "");
//        Socket cliSocket = ss.accept();
//        Socket cliSocket1 = ss.accept();
//        List<Socket> clis = new ArrayList<Socket>();
//        clis.addAmount(cliSocket);
//        clis.addAmount(cliSocket1);
//
//        GameThread gameThread = new GameThread(clis, factory);
//        when(mockPlayer.isWinner(6)).thenReturn(true);
//        when(mockPlayer.isDefeated()).thenReturn(true);
//        when(mockMap.doCombats()).thenReturn("");
//        Field mapField = GameThread.class.getDeclaredField("theMap");
//        Field playerField = GameThread.class.getDeclaredField("players");
//        mapField.setAccessible(true);
//        mapField.set(gameThread, mockMap);
//        playerField.setAccessible(true);
//        playerField.set(gameThread, p);
//
//        ClientHandlerThread cliTh =  gameThread.getClients().get(0);
//        ClientHandlerThread cliTh1 =  gameThread.getClients().get(1);
//        cliTh.reportResult();
//        cliTh1.reportResult();
//        cli.reportResult();
//        cli1.reportResult();
//
//        ss.close();
//        String actual = bytes1.toString().replaceAll("\\r\\n|\\r|\\n", "\n");
//
//        assertEquals("\n" +
//                "null Player:\n" +
//                "-------------\n" +
//                "null Player:\n" +
//                "-------------\n", actual);
//    }
//
//    @Test
//    public void testHandlesIOExceptionInRun() throws Exception {
//        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//        AbstractMapFactory factory = new V1MapFactory();
//        ServerSocket ss = new ServerSocket(1271);
//
//        // Create mock objects
//        BufferedReader mockReader = mock(BufferedReader.class);
//        List<BufferedReader> r = new ArrayList<>();
//        r.addAmount(mockReader);
//
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        PrintStream output = new PrintStream(bytes, true);
//        Socket s = new Socket("localhost", 1271);
//        Client cli = new Client(s, output, in);
//        PrintWriter cliOutput = new PrintWriter(s.getOutputStream(), true);
//
//        Socket cliSocket = ss.accept();
//        List<Socket> clis = new ArrayList<Socket>();
//        clis.addAmount(cliSocket);
//
//        GameThread gameThread = new GameThread(clis, factory);
//        // Set the mock BufferedReader object on the gameThread
//        when(mockReader.readLine()).thenThrow(new IOException("Socket closed"));
//        Field readerField = GameThread.class.getDeclaredField("readers");
//        readerField.setAccessible(true);
//        readerField.set(gameThread, r);
//
//        ClientHandlerThread cliTh =  gameThread.getClients().get(0);
//        cliTh.issueOrders();
//
//        cli.receive();
//        cliOutput.println("M");
//        String END_OF_TURN = "END_OF_TURN\n";
//        cliOutput.print(END_OF_TURN);
//        cliOutput.flush(); // flush the output buffer
//
//        cliTh.interrupt();
//        gameThread.interrupt();
//        gameThread.join();
//
//    }
//    @Test
//    public void testIssueOrdersAttack() throws Exception {
//        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//        String END_OF_TURN = "END_OF_TURN";
//        ServerSocket ss = new ServerSocket(1231);
//        AbstractMapFactory factory = new V1MapFactory();
//
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        ByteArrayOutputStream bytes1 = new ByteArrayOutputStream();
//        Client cli = createClient(1231,"localhost", bytes, "1\n2\n3\n4\n5\nA\n1\na1\nb1\nD\n");
//        Client cli1 = createClient(1231,"localhost", bytes1, "1\n2\n3\n4\n5\nD\n");
//
//        Socket cliSocket = ss.accept();
//        Socket cliSocket1 = ss.accept();
//        List<Socket> clis = new ArrayList<Socket>();
//        clis.addAmount(cliSocket);
//        clis.addAmount(cliSocket1);
//        GameThread th = new GameThread(clis, factory);
//        // create a new thread and start it
//        Thread thread = new Thread(() -> {
//            ClientHandlerThread cliTh = th.getClients().get(0);
//            cliTh.doInitialPlacement();
//            cliTh.issueOrders();
//        });
//        thread.start();
//
//        cli.doInitialPlacement();
//        cli1.doInitialPlacement();
//        cli.receivePlacementResult();
//        cli1.receivePlacementResult();
//        cli.doOneTurn();
//        cli1.doOneTurn();
//        thread.interrupt();
//        thread.join();
//        ss.close();
//        String actual = bytes.toString().replaceAll("\\r\\n|\\r|\\n", "\n");
//
//        assertEquals("Please enter the units you would like to place in a1 (36 units)\n" +
//                "valid\n" +
//                "\n" +
//                "Please enter the units you would like to place in a2 (35 units)\n" +
//                "valid\n" +
//                "\n" +
//                "Please enter the units you would like to place in a3 (33 units)\n" +
//                "valid\n" +
//                "\n" +
//                "Please enter the units you would like to place in a4 (30 units)\n" +
//                "valid\n" +
//                "\n" +
//                "Please enter the units you would like to place in a5 (26 units)\n" +
//                "valid\n" +
//                "\n" +
//                "Placement phase is done!\n" +
//                "\n" +
//                "Green Player:\n" +
//                "-------------\n" +
//                "1 units in a1 (next to: b1, a2)\n" +
//                "2 units in a2 (next to: a1, b2, a3)\n" +
//                "3 units in a3 (next to: a2, b3, a4)\n" +
//                "4 units in a4 (next to: a3, b4, a5)\n" +
//                "5 units in a5 (next to: a4, b5, a6)\n" +
//                "21 units in a6 (next to: a5, b6)\n" +
//                "Red Player:\n" +
//                "-------------\n" +
//                "1 units in b1 (next to: a1, b2)\n" +
//                "2 units in b2 (next to: a2, b1, b3)\n" +
//                "3 units in b3 (next to: a3, b2, b4)\n" +
//                "4 units in b4 (next to: a4, b3, b5)\n" +
//                "5 units in b5 (next to: a5, b4, b6)\n" +
//                "21 units in b6 (next to: a6, b5)\n" +
//                "You are the Green player, what would you like to do?\n" +
//                "(M)ove\n" +
//                "(A)ttack\n" +
//                "(D)one\n"+
//                "Please enter the number of units to attack:\n" +
//                "Please enter the source territory:\n" +
//                "Please enter the destination territory:\n" +
//                "You are the Green player, what would you like to do?\n" +
//                "(M)ove\n" +
//                "(A)ttack\n" +
//                "(D)one\n", actual);
//    }
//
//    @Disabled
//    @Test
//    public void testCheckUnitNumValid() throws IOException, InterruptedException {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        ServerSocket socket = new ServerSocket(1231);
//        AbstractMapFactory factory = new V1MapFactory();
//
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        Client client = createClient(1231,"localhost", bytes, "1\n1\n");
//
//        Socket cliSocket = socket.accept();
//        List<Socket> clients = new ArrayList<>();
//
//        clients.addAmount(cliSocket);
//        GameThread thread = new GameThread(clients, factory);
//        thread.start();
//
//        ClientHandlerThread cliTh = thread.getClients().get(0);
//        assertTrue(cliTh.checkUnitNumValid(5, 0, 6));
////
////        client.run();
////
////        thread.interrupt();
////        thread.join();
////        socket.close();
////        String actual = bytes.toString().replaceAll("\\r\\n|\\r|\\n", "\n");
//
//    }
}