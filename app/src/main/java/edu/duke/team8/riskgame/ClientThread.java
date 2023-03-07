package edu.duke.team8.riskgame;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket clientSocket;
    private String color;
    private String mapInfo;
    private final BufferedReader input;
    private final PrintWriter output;
    /** Boolean indicate whether the thread is running or not*/
    private boolean isRunning;

    /**
     * Constructor of ClientThread
     * @param clientSocket is the socket of the thread
     * @param color is color assigned to this client
     * @param mapInfo is map information
     * @throws IOException
     */
    public ClientThread(Socket clientSocket, String color, String mapInfo) throws IOException {
        this.clientSocket = clientSocket;
        this.color = color;
        this.mapInfo = mapInfo;
        this.input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.output = new PrintWriter(clientSocket.getOutputStream(), true);
        this.isRunning = true;
    }
    @Override
    public void run() {
        while (isRunning) {
            send();
        }
    }

    /**
     * Stop the current thread
     */
    public void stopThread() {
        isRunning = false;
        try {
            clientSocket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * Send string info to client
     */
    public void send(){
        output.println(color);
        output.println(mapInfo);
        output.close();
    }

    /**
     * Get the socket of clientThread
     * @return socket of the thread
     */
    public Socket getSocket() {
        return clientSocket;
    }
}
