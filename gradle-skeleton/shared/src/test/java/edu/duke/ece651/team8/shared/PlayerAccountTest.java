package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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
        assertEquals("asd", acc.getUsername());
        assertEquals(true, acc.isEmpty());
    }

    @Test
    void getUsername() {
    }

    @Test
    void updateIO() {
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
    void addJoinGame() {
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