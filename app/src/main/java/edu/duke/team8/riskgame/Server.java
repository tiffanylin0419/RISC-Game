package edu.duke.team8.riskgame;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    /** info to be transfer */
    protected String info;
    /** server socket*/
    protected ServerSocket server;
    /** Map of the game */
    private final Map theMap;
    protected View mapView;
    /**
     * Constructs a server with specified port
     *
     * @param port is the port of the socket
     * @param theMap is the map of the board
     */
    public Server(int port, Map theMap) throws IOException {
        this(new ServerSocket(port), theMap);

    }
    public Server(ServerSocket ss, Map theMap) {
        this.server = ss;
        this.theMap = theMap;
        this.mapView = new MapTextView(theMap);
        this.info = mapView.displayMap();
    }
    /**
     * @return the port of the server socket
     */
    public int getPort() {
        return server.getLocalPort();
    }
    /** Execute the server */
    public void run() {
        try {

            System.out.println("Waiting for client connection...");

            Socket clientSocket = server.accept();

            System.out.println("Client connected!");
            send(clientSocket);

            clientSocket.close();
            server.close();
        } catch (IOException e) {
            System.out.println("Out/Input stream error");
        }
    }
    /**
     * Send string info to client
     * @param client is the socket for cclient
     */
    public void send(Socket client) throws IOException {

        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
        System.out.println("handle");
        out.println(info);

    }

}

