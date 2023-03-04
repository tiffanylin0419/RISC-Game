package edu.duke.team8.riskgame;

import java.io.IOException;
import java.io.PrintStream;
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
    /** View of the map */
    protected View mapView;
    /** Output stream of the Server*/
    protected PrintStream out;
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
        this(ss, theMap, System.out);
    }
    public Server(ServerSocket ss, Map theMap, PrintStream out) {
        this.server = ss;
        this.theMap = theMap;
        this.mapView = new MapTextView(theMap);
        this.info = mapView.displayMap();
        this.out = out;
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
            out.println("Out/Input stream error");
        }
    }
    /**
     * Send string info to client
     * @param client is the socket for cclient
     */
    public void send(Socket client) throws IOException {

        PrintWriter outInfo = new PrintWriter(client.getOutputStream(), true);
        outInfo.println(info);

    }

}

