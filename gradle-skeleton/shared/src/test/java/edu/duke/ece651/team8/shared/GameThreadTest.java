package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import java.io.*;
import java.lang.reflect.Field;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

//
@ExtendWith(MockitoExtension.class)
class GameThreadTest {
    @Spy
    private List<ClientHandlerThread> clientThreads =  new ArrayList<>();
    @Mock
    private Object lock;
    @Mock
    /** streams pass to client*/
    private List<PrintWriter> outputs;
    @Mock
    /** Reader for clients message*/
    private List<BufferedReader> readers;
    @Mock
    /** Map of the game */
    private Map theMap;
    @Mock
    /** View of the map */
    protected View mapView;
    @Mock
    private ArrayList<Player> players;
    @Mock
    private List<PlayerAccount> accounts;

    private static class TestGameThread extends GameThread{
        public TestGameThread(){
            super(2,new V2MapFactory(),0);

        }
    }
    @InjectMocks
    private TestGameThread testGameThread;

    @Test
    public void TestCheckFinish(){
        ClientHandlerThread cht = mock(ClientHandlerThread.class);
        ClientHandlerThread cht1 = mock(ClientHandlerThread.class);
        clientThreads.add(cht);
        clientThreads.add(cht1);
        when(cht.getStatus()).thenReturn(-1,-1);
        when(cht1.getStatus()).thenReturn(-1,1);

        assertEquals(testGameThread.checkFinish(),false);
        assertEquals(testGameThread.checkFinish(),true);

    }
//    public Client createClient(int port, String host, OutputStream bytes, String inputData)throws IOException{
//        BufferedReader input = new BufferedReader(new StringReader(inputData));
//        PrintStream out = new PrintStream(bytes, true);
//        return  new Client(port, host, out, input);
//    }
//    private void doClientAction(Client cli, GameThread th) throws Exception {
//        cli.receiveColor();
//        cli.receiveMap();
//        cli.displayColor();
//        cli.displayMap();
//        cli.doInitialPlacement();
//        cli.receivePlacementResult();
//        Thread.sleep(20);
//        th.setWinner("Green");
//        cli.doOneTurn();
//        cli.reportResult();
//    }
//
//    @Test
//    public void testRun() throws Exception {
//        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//        ServerSocket ss = new ServerSocket(1231);
//        AbstractMapFactory factory = new V1MapFactory();
//
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        Client cli = createClient(1231,"localhost", bytes, "1\n2\n3\n4\n5\n6\nM\n1\na1\na2\nD\n");
//
//        Socket cliSocket = ss.accept();
//        List<Socket> clis = new ArrayList<>();
//
//        clis.addAmount(cliSocket);
//        GameThread th = new GameThread(clis, factory);
//        th.start();
//
//        doClientAction(cli, th);
//
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
//                "1 units in a1 (next to: a2)\n" +
//                "2 units in a2 (next to: a1, a3)\n" +
//                "3 units in a3 (next to: a2, a4)\n" +
//                "4 units in a4 (next to: a3, a5)\n" +
//                "5 units in a5 (next to: a4, a6)\n" +
//                "21 units in a6 (next to: a5)\n" +
//                "You are the Green player, what would you like to do?\n" +
//                "(M)ove\n" +
//                "(A)ttack\n" +
//                "(D)one\n"+
//                "Action should be \"M\"(move) \"A\"(attack) or \"D\"(done)\n" +
//                "You are the Green player, what would you like to do?\n" +
//                "(M)ove\n" +
//                "(A)ttack\n" +
//                "(D)one\n"+
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
//                "1 units in a1 (next to: a2)\n" +
//                "4 units in a2 (next to: a1, a3)\n" +
//                "4 units in a3 (next to: a2, a4)\n" +
//                "5 units in a4 (next to: a3, a5)\n" +
//                "6 units in a5 (next to: a4, a6)\n" +
//                "22 units in a6 (next to: a5)\nCongratulations! You win!\n", actual);
//    }
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
//        doClientAction(cli, th);
//
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
//        gameThread.reportResults();
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
//        gameThread.issueOrders();
//
//        cli.receive();
//        cliOutput.println("M");
//        String END_OF_TURN = "END_OF_TURN\n";
//        cliOutput.print(END_OF_TURN);
//        cliOutput.flush(); // flush the output buffer
//
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
//            th.doInitialPlacement();
//            th.issueOrders();
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
//        assertTrue(thread.checkUnitNumValid(5, 0, 6));
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