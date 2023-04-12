package edu.duke.ece651.team8.client;

import edu.duke.ece651.team8.shared.AbstractMapFactory;
import edu.duke.ece651.team8.shared.Server;
import edu.duke.ece651.team8.shared.V1MapFactory;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientTest {

    @Test
    public void testConstructor() throws Exception {
        AbstractMapFactory factory = new V1MapFactory();
        ServerSocket ss = new ServerSocket(1237);

        Server s = new Server(ss, factory);
        Thread serverThread = new Thread(() -> {
            s.run();
        });
        serverThread.start();

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Client clis = new Client(1237, "localhost", in);
        s.stop();
        serverThread.join();
        ss.close();
    }

    @Test
    public void testjoinNewGame() throws Exception {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
//
        PrintWriter out = mock(PrintWriter.class);
        BufferedReader clientReader = mock(BufferedReader.class);
        BufferedReader ServerReader = mock(BufferedReader.class);
        InputStream instream = mock(InputStream.class);
        Client client = new Client(clientReader, ServerReader, output, out, instream);
        when(ServerReader.readLine()).thenReturn("L/S").thenReturn("END_OF_TURN").thenReturn("username").thenReturn("END_OF_TURN").thenReturn("password").
                thenReturn("END_OF_TURN").thenReturn("Successfully login!").thenReturn("END_OF_TURN").thenReturn("choosegame").thenReturn("END_OF_TURN").
                thenReturn("number").thenReturn("END_OF_TURN").thenReturn("wait").thenReturn("END_OF_TURN");

        when(clientReader.readLine()).thenReturn("S").thenReturn("qwe").thenReturn("123").thenReturn("N").
                thenReturn("2");

        client.doLoginOrSignup();
        client.doChooseGame();
//        when(clientReader.readLine()).thenReturn("abc").thenReturn("123").thenReturn("").thenReturn(client.END_OF_TURN);
//        client.mapInfo = "abc\n123";
//        client.displayMap();
//        String actual = bytes.toString();
//        assertEquals("abc\n123\n", actual);
    }
    @Test
    public void testJoinOldGameAndRun() throws Exception {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
//
        PrintWriter out = mock(PrintWriter.class);
        BufferedReader clientReader = mock(BufferedReader.class);
        BufferedReader ServerReader = mock(BufferedReader.class);
        InputStream instream = mock(InputStream.class);
        Client client = new Client(clientReader, ServerReader, output, out, instream);
        when(ServerReader.readLine()).thenReturn("L/S").thenReturn("END_OF_TURN").thenReturn("username").thenReturn("END_OF_TURN").thenReturn("password").
                thenReturn("END_OF_TURN").thenReturn("Successfully login!").thenReturn("END_OF_TURN").thenReturn("choosegame").thenReturn("END_OF_TURN").
                thenReturn("gamelist").thenReturn("END_OF_TURN").thenReturn("0.game").thenReturn("END_OF_TURN").thenReturn("4").thenReturn("END_OF_TURN").
                thenReturn("blue").thenReturn("END_OF_TURN").thenReturn("playerinfo").thenReturn("END_OF_TURN").thenReturn("outcome").thenReturn("END_OF_TURN").
                thenReturn("map").thenReturn("END_OF_TURN").thenReturn("notlose").thenReturn("END_OF_TURN").
                thenReturn("no winner").thenReturn("END_OF_TURN").thenReturn("prompt").thenReturn("END_OF_TURN").
                thenReturn("unitnumber").thenReturn("END_OF_TURN").thenReturn("source").thenReturn("END_OF_TURN").
                thenReturn("target").thenReturn("END_OF_TURN").thenReturn("").thenReturn("END_OF_TURN").
                thenReturn("color").thenReturn("END_OF_TURN").thenReturn("prompt").thenReturn("END_OF_TURN").
                thenReturn("unitnumber").thenReturn("END_OF_TURN").thenReturn("source").thenReturn("END_OF_TURN").
                thenReturn("target").thenReturn("END_OF_TURN").thenReturn("").thenReturn("END_OF_TURN").
                thenReturn("color").thenReturn("END_OF_TURN").thenReturn("prompt").thenReturn("END_OF_TURN").
                thenReturn("terri").thenReturn("END_OF_TURN").thenReturn("number").thenReturn("END_OF_TURN").
                thenReturn("level").thenReturn("END_OF_TURN").thenReturn("level").thenReturn("END_OF_TURN").
                thenReturn("").thenReturn("END_OF_TURN").thenReturn("color").thenReturn("END_OF_TURN").
                thenReturn("playerinfo").thenReturn("END_OF_TURN").thenReturn("outcome").thenReturn("END_OF_TURN").
                thenReturn("map").thenReturn("END_OF_TURN").thenReturn("notlose").thenReturn("END_OF_TURN").
                thenReturn("winner").thenReturn("END_OF_TURN");

                when(clientReader.readLine()).thenReturn("L").thenReturn("qwe").thenReturn("123").thenReturn("Y").
                        thenReturn("2").thenReturn("0").thenReturn("M").thenReturn("2").thenReturn("a1").thenReturn("a2").
                thenReturn("A").thenReturn("3").thenReturn("a1").thenReturn("b1").
                thenReturn("U").thenReturn("a1").thenReturn("3").thenReturn("0").thenReturn("1").thenReturn("D");
        client.stop();
        client.run();
    }
    @Test
    public void testInitialPlacement() throws Exception {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream output = new PrintStream(bytes, true);
//
        PrintWriter out = mock(PrintWriter.class);
        BufferedReader clientReader = mock(BufferedReader.class);
        BufferedReader ServerReader = mock(BufferedReader.class);
        InputStream instream = mock(InputStream.class);
        Client client = new Client(clientReader, ServerReader, output, out, instream);
        when(ServerReader.readLine()).thenReturn("3").thenReturn("END_OF_TURN").thenReturn("prompt").thenReturn("END_OF_TURN").thenReturn("valid\n").
                thenReturn("END_OF_TURN").thenReturn("prompt").thenReturn("END_OF_TURN").thenReturn("valid\n").thenReturn("END_OF_TURN").
                thenReturn("prompt").thenReturn("END_OF_TURN").thenReturn("result").thenReturn("END_OF_TURN").thenReturn("map").thenReturn("END_OF_TURN");

        when(clientReader.readLine()).thenReturn("3").thenReturn("3");

        client.doInitialPlacement();
        client.receivePlacementResult();
//        when(clientReader.readLine()).thenReturn("abc").thenReturn("123").thenReturn("").thenReturn(client.END_OF_TURN);
//        client.mapInfo = "abc\n123";
//        client.displayMap();
//        String actual = bytes.toString();
//        assertEquals("abc\n123\n", actual);
    }
//
//    @Test
//    public void testReceive()throws IOException{
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(bytes, true);
//        PrintWriter output = mock(PrintWriter.class);
//        Socket mockSocket = mock(Socket.class);
//        InputStream inputStream = mock(InputStream.class);
//        BufferedReader mockServerBuffer = mock(BufferedReader.class);
//        Client client = createClient(mockServerBuffer,mockSocket,out,inputStream,output,"");
//
//        when(mockServerBuffer.readLine()).thenReturn("abc").thenReturn("123").thenReturn("").thenReturn(client.END_OF_TURN);
//        client.receive();
//        assertEquals(client.buffer,"abc\n123\n");
//    }
//
//    @Test
//    public void testSend()throws IOException{
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(bytes, true);
//        PrintWriter output = mock(PrintWriter.class);
//        Socket mockSocket = mock(Socket.class);
//        InputStream inputStream = mock(InputStream.class);
//        BufferedReader mockServerBuffer = mock(BufferedReader.class);
//        Client client = createClient(mockServerBuffer,mockSocket,out,inputStream,output,"");
//
//        client.send("abc\n123\n");
//        verify(output).println(eq("abc\n123\n"));
//        verify(output).println(eq(client.END_OF_TURN));
//        verify(output).flush();
//    }
//
//    @Test
//    public void testReceiveAndDisplayColor()throws IOException{
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(bytes, true);
//        PrintWriter output = mock(PrintWriter.class);
//        Socket mockSocket = mock(Socket.class);
//        InputStream inputStream = mock(InputStream.class);
//        BufferedReader mockServerBuffer = mock(BufferedReader.class);
//        Client client = createClient(mockServerBuffer,mockSocket,out,inputStream,output,"");
//
//        when(mockServerBuffer.readLine()).thenReturn("green").thenReturn(client.END_OF_TURN);
//        client.receiveColor();
//        assertEquals(client.color,"green");
//        client.displayColor();
//        String actual = bytes.toString();
//        assertEquals("green\n", actual);
//    }
//
//    @Test
//    public void testReceiveAndDisplayMap()throws IOException{
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(bytes, true);
//        PrintWriter output = mock(PrintWriter.class);
//        Socket mockSocket = mock(Socket.class);
//        InputStream inputStream = mock(InputStream.class);
//        BufferedReader mockServerBuffer = mock(BufferedReader.class);
//        Client client = createClient(mockServerBuffer,mockSocket,out,inputStream,output,"");
//
//        when(mockServerBuffer.readLine()).thenReturn("-------------\n" +
//                "0 units in a1 (next to: a2)\n" +
//                "0 units in a2 (next to: a1, a3)\n" +
//                "0 units in a3 (next to: a2, a4)\n" +
//                "0 units in a4 (next to: a3, a5)\n" +
//                "0 units in a5 (next to: a4, a6)\n" +
//                "0 units in a6 (next to: a5)").thenReturn(client.END_OF_TURN);
//        client.receiveMap();
//        assertEquals(client.mapInfo,"-------------\n" +
//                "0 units in a1 (next to: a2)\n" +
//                "0 units in a2 (next to: a1, a3)\n" +
//                "0 units in a3 (next to: a2, a4)\n" +
//                "0 units in a4 (next to: a3, a5)\n" +
//                "0 units in a5 (next to: a4, a6)\n" +
//                "0 units in a6 (next to: a5)");
//        client.displayMap();
//        String actual = bytes.toString();
//        assertEquals("-------------\n" +
//                "0 units in a1 (next to: a2)\n" +
//                "0 units in a2 (next to: a1, a3)\n" +
//                "0 units in a3 (next to: a2, a4)\n" +
//                "0 units in a4 (next to: a3, a5)\n" +
//                "0 units in a5 (next to: a4, a6)\n" +
//                "0 units in a6 (next to: a5)\n", actual);
//    }
//
//    @Test
//    public void testReceiveAndDisplayCombatOutcome()throws IOException{
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(bytes, true);
//        PrintWriter output = mock(PrintWriter.class);
//        Socket mockSocket = mock(Socket.class);
//        InputStream inputStream = mock(InputStream.class);
//        BufferedReader mockServerBuffer = mock(BufferedReader.class);
//        Client client = createClient(mockServerBuffer,mockSocket,out,inputStream,output,"");
//
//        when(mockServerBuffer.readLine()).thenReturn("abc").thenReturn("123").thenReturn("").thenReturn(client.END_OF_TURN);
//        client.receiveCombatOutcome();
//        assertEquals(client.combatOutcome,"abc\n123\n");
//        client.displayCombatOutcome();
//        String actual = bytes.toString();
//        assertEquals("abc\n123\n\n", actual);
//
//    }
//    @Test
//    public void testReceiveWinner()throws IOException{
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(bytes, true);
//        PrintWriter output = mock(PrintWriter.class);
//        Socket mockSocket = mock(Socket.class);
//        InputStream inputStream = mock(InputStream.class);
//        BufferedReader mockServerBuffer = mock(BufferedReader.class);
//        Client client = createClient(mockServerBuffer,mockSocket,out,inputStream,output,"");
//
//        when(mockServerBuffer.readLine()).thenReturn("red").thenReturn(client.END_OF_TURN);
//        client.receiveWinner();
//        assertEquals(client.winner,"red");
//    }
//
//    @Test
//    public void testReceiveLoseStatus()throws IOException{
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(bytes, true);
//        PrintWriter output = mock(PrintWriter.class);
//        Socket mockSocket = mock(Socket.class);
//        InputStream inputStream = mock(InputStream.class);
//        BufferedReader mockServerBuffer = mock(BufferedReader.class);
//        Client client = createClient(mockServerBuffer,mockSocket,out,inputStream,output,"A");
//
//        when(mockServerBuffer.readLine()).thenReturn("continue").thenReturn(client.END_OF_TURN).thenReturn("lose").thenReturn(client.END_OF_TURN);
//        client.receiveLoseStatus();
//        assertEquals(client.isDefeated,false);
//        client.receiveLoseStatus();
//        assertEquals(client.isDefeated,true);
//    }
//
//    @Test
//    public void testTryDoInitialPlacement()throws IOException{
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(bytes, true);
//        PrintWriter output = mock(PrintWriter.class);
//        Socket mockSocket = mock(Socket.class);
//        InputStream inputStream = mock(InputStream.class);
//        BufferedReader mockServerBuffer = mock(BufferedReader.class);
//        Client client = createClient(mockServerBuffer,mockSocket,out,inputStream,output,"6\n100\na\n8\n");
//        when(mockServerBuffer.readLine()).thenReturn("2").thenReturn(client.END_OF_TURN).thenReturn("Please enter the units you would like to place in a1 (36 units)\n").thenReturn(client.END_OF_TURN).thenReturn("valid\n").thenReturn(client.END_OF_TURN).thenReturn("Please enter the units you would like to place in a2 (36 units)\n").thenReturn(client.END_OF_TURN).thenReturn("invalid\n").thenReturn(client.END_OF_TURN).thenReturn("Please enter the units you would like to place in a3 (36 units)\n").thenReturn(client.END_OF_TURN).thenReturn("valid\n").thenReturn(client.END_OF_TURN);
//
//        client.doInitialPlacement();
//        String actual = bytes.toString();
//        assertEquals("Please enter the units you would like to place in a1 (36 units)\n" +
//                "valid\n" +
//                "\n" +
//                "Please enter the units you would like to place in a2 (36 units)\n" +
//                "invalid\n" +
//                "\n" +
//                "Please enter the units you would like to place in a3 (36 units)\n" +
//                "For input string: \"a\"\n" +
//                "Please input a valid placement!\n" +
//                "Please enter the units you would like to place in a3 (36 units)\n" +
//                "valid\n" +
//                "\n", actual);
//        InOrder inOrder = inOrder(output);
//
//        inOrder.verify(output).println("6");
//        inOrder.verify(output).println(client.END_OF_TURN);
//        inOrder.verify(output).flush();
//        inOrder.verify(output).println("100");
//        inOrder.verify(output).println(client.END_OF_TURN);
//        inOrder.verify(output).flush();
//        inOrder.verify(output).println("8");
//        inOrder.verify(output).println(client.END_OF_TURN);
//        inOrder.verify(output).flush();
//        bytes.reset();
//    }
//    @Test
//    public void testReportResult()throws IOException{
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(bytes, true);
//        PrintWriter output = mock(PrintWriter.class);
//        Socket mockSocket = mock(Socket.class);
//        InputStream inputStream = mock(InputStream.class);
//        BufferedReader mockServerBuffer = mock(BufferedReader.class);
//        Client client = createClient(mockServerBuffer,mockSocket,out,inputStream,output,"a\n6\n");
//        client.color = "red";
//        when(mockServerBuffer.readLine()).thenReturn("CombatOutcome").thenReturn(client.END_OF_TURN).thenReturn("Map").thenReturn(client.END_OF_TURN).thenReturn("continue").thenReturn(client.END_OF_TURN).thenReturn("no winner").thenReturn(client.END_OF_TURN).thenReturn("CombatOutcome").thenReturn(client.END_OF_TURN).thenReturn("Map").thenReturn(client.END_OF_TURN).thenReturn("continue").thenReturn(client.END_OF_TURN).thenReturn("red").thenReturn(client.END_OF_TURN).thenReturn("CombatOutcome").thenReturn(client.END_OF_TURN).thenReturn("Map").thenReturn(client.END_OF_TURN).thenReturn("lose").thenReturn(client.END_OF_TURN).thenReturn("no winner").thenReturn(client.END_OF_TURN).thenReturn("CombatOutcome").thenReturn(client.END_OF_TURN).thenReturn("Map").thenReturn(client.END_OF_TURN).thenReturn("lose").thenReturn(client.END_OF_TURN).thenReturn("blue").thenReturn(client.END_OF_TURN);
//
//        client.reportResult();
//        String actual = bytes.toString();
//        assertEquals("CombatOutcome\n" +
//                "Map\n", actual);
//
//        bytes.reset();
//        client.winner = "no winner";
//        client.reportResult();
//        actual = bytes.toString();
//        assertEquals("CombatOutcome\n" +
//                "Map\n" +
//                "Congratulations! You win!\n", actual);
//
//        bytes.reset();
//        client.reportResult();
//        actual = bytes.toString();
//        assertEquals("CombatOutcome\n" +
//                "Map\n" +
//                "You lose.\n", actual);
//
//        bytes.reset();
//        client.reportResult();
//        actual = bytes.toString();
//        assertEquals("CombatOutcome\n" +
//                "Map\n" +
//                "blue wins.\n", actual);
//
//        bytes.reset();
//    }
//    @Test
//    public void testTryInputUnitNumberToPlace()throws Exception{
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(bytes, true);
//        PrintWriter output = mock(PrintWriter.class);
//        Socket mockSocket = mock(Socket.class);
//        InputStream inputStream = mock(InputStream.class);
//        BufferedReader mockServerBuffer = mock(BufferedReader.class);
//        Client client = createClient(mockServerBuffer,mockSocket,out,inputStream,output,"a\n6\n");
//
//        assertThrows(NumberFormatException.class,()->client.tryInputUnitNumberToPlace("Please enter the units you would like to place in a1 (36 units)\n",client.input));
//        bytes.reset();
//        client.tryInputUnitNumberToPlace("Please enter the units you would like to place in a1 (36 units)\n",client.input);
//        String actual = bytes.toString();
//        assertEquals("Please enter the units you would like to place in a1 (36 units)\n", actual);
//        verify(output).println("6");
//        verify(output).println(client.END_OF_TURN);
//        verify(output).flush();
//
//    }
//
//    @Test
//    public void testDoOneTurn()throws IOException{
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(bytes, true);
//        PrintWriter output = mock(PrintWriter.class);
//        Socket mockSocket = mock(Socket.class);
//        InputStream inputStream = mock(InputStream.class);
//        BufferedReader mockServerBuffer = mock(BufferedReader.class);
//        Client client = createClient(mockServerBuffer,mockSocket,out,inputStream,output,"m\nM\n6\na1\na2\nM\n6\na2\nb2\nA\n6\na1\na2\nA\n6\na2\nb2\nD\n");
//        when(mockServerBuffer.readLine()).thenReturn("What would you like to do?\n(M)ove\n(A)ttack\n(D)one\n").thenReturn(client.END_OF_TURN).thenReturn("Please enter the number of units to move:\n").thenReturn(client.END_OF_TURN).thenReturn("Please enter the source territory:\n").thenReturn(client.END_OF_TURN).thenReturn("Please enter the destination territory:\n").thenReturn(client.END_OF_TURN).thenReturn("invalid input\n").thenReturn(client.END_OF_TURN).thenReturn("Please enter the number of units to move:\n").thenReturn(client.END_OF_TURN).thenReturn("Please enter the source territory:\n").thenReturn(client.END_OF_TURN).thenReturn("Please enter the destination territory:\n").thenReturn(client.END_OF_TURN).thenReturn("").thenReturn(client.END_OF_TURN).thenReturn("What would you like to do?\n(M)ove\n(A)ttack\n(D)one\n").thenReturn(client.END_OF_TURN).thenReturn("Please enter the number of units to attack:\n").thenReturn(client.END_OF_TURN).thenReturn("Please enter the source territory:\n").thenReturn(client.END_OF_TURN).thenReturn("Please enter the destination territory:\n").thenReturn(client.END_OF_TURN).thenReturn("invalid input\n").thenReturn(client.END_OF_TURN).thenReturn("Please enter the number of units to attack:\n").thenReturn(client.END_OF_TURN).thenReturn("Please enter the source territory:\n").thenReturn(client.END_OF_TURN).thenReturn("Please enter the destination territory:\n").thenReturn(client.END_OF_TURN).thenReturn("").thenReturn(client.END_OF_TURN).thenReturn("What would you like to do?\n(M)ove\n(A)ttack\n(D)one").thenReturn(client.END_OF_TURN);
//
//        client.doOneTurn();
//        String actual = bytes.toString();
//        assertEquals("What would you like to do?\n" +
//                "(M)ove\n" +
//                "(A)ttack\n" +
//                "(D)one\nAction should be \"M\"(move) \"A\"(attack) or \"D\"(done)\n" +
//                "What would you like to do?\n" +
//                "(M)ove\n" +
//                "(A)ttack\n" +
//                "(D)one\n" +
//                "Please enter the number of units to move:\n" +
//                "\n" +
//                "Please enter the source territory:\n" +
//                "\n" +
//                "Please enter the destination territory:\n" +
//                "\n" +
//                "invalid input\n" +
//                "\n" +
//                "Please enter the number of units to move:\n" +
//                //"\n" +
//                "Please enter the source territory:\n" +
//                "\n" +
//                "Please enter the destination territory:\n" +
//                "\n"+
//                "\n"+
//                "What would you like to do?\n" +
//                "(M)ove\n" +
//                "(A)ttack\n" +
//                "(D)one\n"+
//                "\n"+
//                "Please enter the number of units to attack:\n" +
//                //"\n"+
//                "Please enter the source territory:\n" +
//                "\n" +
//                "Please enter the destination territory:\n" +
//                "\n" +
//                "invalid input\n" +
//                "\n" +
//                "Please enter the number of units to attack:\n" +
//                "\n" +
//                "Please enter the source territory:\n" +
//                //"\n" +
//                "Please enter the destination territory:\n" +
//                "\n" +
//                "\n"+
//                "What would you like to do?\n" +
//                "(M)ove\n" +
//                "(A)ttack\n" +
//                "(D)one\n"+
//                "END_OF_TURN\n"+
//                "END_OF_TURN", actual);
//        bytes.reset();
//    }
//    @Test
//    public void testTryChooseOneAction()throws IOException{
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(bytes, true);
//        PrintWriter output = mock(PrintWriter.class);
//        Socket mockSocket = mock(Socket.class);
//        InputStream inputStream = mock(InputStream.class);
//        BufferedReader mockServerBuffer = mock(BufferedReader.class);
//        Client client = createClient(mockServerBuffer,mockSocket,out,inputStream,output,"A\na\n");
//
//        client.tryChooseOneAction("What would you like to do?\n(M)ove\n(A)ttack\n(D)one",client.input);
//        String actual = bytes.toString();
//        assertEquals("What would you like to do?\n(M)ove\n(A)ttack\n(D)one", actual);
//        verify(output).println("A");
//        verify(output).println(client.END_OF_TURN);
//        verify(output).flush();
//        bytes.reset();
//        assertThrows(IllegalArgumentException.class,()->client.tryChooseOneAction("What would you like to do?\n(M)ove\n(A)ttack\n(D)one",client.input));
//        actual = bytes.toString();
//        assertEquals("What would you like to do?\n(M)ove\n(A)ttack\n(D)one", actual);
//
//
//    }
//
//    @Test
//    public void testIsOver()throws IOException{
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(bytes, true);
//        PrintWriter output = mock(PrintWriter.class);
//        Socket mockSocket = mock(Socket.class);
//        InputStream inputStream = mock(InputStream.class);
//        BufferedReader mockServerBuffer = mock(BufferedReader.class);
//        Client client = createClient(mockServerBuffer,mockSocket,out,inputStream,output,"A");
//        assertEquals(client.isOver(),false);
//    }
}
