package edu.duke.team8.riskgame;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
    /** Color list of players */
    private final List<String> colorList;
    /** List of the client sockets */
    protected List<Socket> clients;
    /** number of clients */
    protected int clientNum;
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
        this(ss, theMap, System.out, 4);
    }
    /**
     * @param clientNum is the number of clients
     */
    public Server(ServerSocket ss, Map theMap, int clientNum) {
        this(ss, theMap, System.out, clientNum);
    }
    public Server(ServerSocket ss, Map theMap, PrintStream out, int clientNum) {
        this.server = ss;
        this.theMap = theMap;
        this.mapView = new MapTextView(theMap);
        this.info = mapView.displayMap();
        this.out = out;
        this.clients = new ArrayList<Socket>();
        this.clientNum = clientNum;
        this.colorList = new ArrayList<String>();
        String colors[] = { "Green", "Red", "Blue", "Yellow" };
        for(int i = 0; i < clientNum; i++) {
            colorList.add(colors[i]);
        }
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
            for (int i = 0; i < clientNum; i++) {
                connectOneClient(i);
            }
            server.close();
        } catch (IOException e) {
            out.println("Out/Input stream error");
        }
    }
    /** Execute the server to connect with one client
     * @param clientId is the index of the client
     */
    public void connectOneClient(int clientId) throws IOException {
            System.out.println("Waiting for client " + clientId + " connection...");
            Socket clientSocket = server.accept();
            System.out.println("Client connected!");
            send(clientSocket, clientId);

            clientSocket.close();
    }
    /**
     * Send string info to client
     * @param client is the socket for cclient
     * @param clientId is the index of the client
     */
    public void send(Socket client, int clientId) throws IOException {

        PrintWriter outInfo = new PrintWriter(client.getOutputStream(), true);
        outInfo.println(colorList.get(clientId));
        outInfo.println(info);
        outInfo.close();
    }

}

