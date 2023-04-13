package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.AbstractMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlayerAccountTest {

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    @Test
    void match() throws IOException {
        // Mock the PrintWriter and BufferedReader
        writer = mock(PrintWriter.class);
        reader = mock(BufferedReader.class);

        // Create a socket for testing
        socket = mock(Socket.class);
        when(socket.getOutputStream()).thenReturn(mock(OutputStream.class));
        when(socket.getInputStream()).thenReturn(mock(InputStream.class));
        PlayerAccount acc = new PlayerAccount(writer, reader, "asd", "123");
        assertEquals(true, acc.match("asd", "123"));
        assertEquals(false, acc.match("ad", "123"));
        assertEquals("asd", acc.getUsername());
        assertEquals(true, acc.isEmpty());
        acc.getOutput();
        acc.getReader();
    }

    @Test
    void updateIO()throws IOException{
        // Mock the PrintWriter and BufferedReader
        writer = mock(PrintWriter.class);
        reader = mock(BufferedReader.class);
        PrintWriter writer1 = mock(PrintWriter.class);
        BufferedReader reader1 = mock(BufferedReader.class);

        // Create a socket for testing
        socket = mock(Socket.class);
        when(socket.getOutputStream()).thenReturn(mock(OutputStream.class));
        when(socket.getInputStream()).thenReturn(mock(InputStream.class));
        PlayerAccount acc = new PlayerAccount(writer, reader, "asd", "123");
        acc.updateIO(writer1,reader1);
    }

    @Test
    void isEmpty() {
    }

    @Test
    void getOutput() {
    }

    @Test
    void getReader() {
    }

    @Test
    void addJoinGame() throws IOException{
        // Mock the PrintWriter and BufferedReader
        writer = mock(PrintWriter.class);
        reader = mock(BufferedReader.class);

        // Create a socket for testing
        socket = mock(Socket.class);
        when(socket.getOutputStream()).thenReturn(mock(OutputStream.class));
        when(socket.getInputStream()).thenReturn(mock(InputStream.class));
        PlayerAccount acc = new PlayerAccount(writer, reader, "asd", "123");


        AbstractMapFactory f = new V2MapFactory();
        GameThread th = new GameThread(2, f, 0);
        acc.addJoinGame(th, 0);
        assertEquals("0. Game 0: 2 players\n", acc.displayGameList());
//        assertEquals(th, acc.select(0));
        acc.deleteEndGame(th, 0);
        assertEquals("", acc.displayGameList());
    }

    @Test
    void deleteEndGame() {
    }

    @Test
    void displayGameList() {
    }

    @Test
    void select() {
    }
}