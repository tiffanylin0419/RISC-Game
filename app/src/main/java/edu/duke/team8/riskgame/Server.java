package edu.duke.team8.riskgame;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    /** map info to be transfer */
    protected String mapInfo;
    /** server socket*/
    protected ServerSocket server;
    /** Map of the game */
    private final Map theMap;
    /** View of the map */
    protected View mapView;
    /** Color list of players */
    private final List<String> colorList;
    /** List of the client sockets */
    private List<ClientThread> clients;
    /** number of clients */
    protected int clientNum;
    /** Boolean indicate whether the server is listening or not*/
    private boolean isListening;
    /**
     * Constructs a server with specified port
     *
     * @param port is the port of the socket
     * @param theMap is the map of the board
     */
    public Server(int port, Map theMap, int clientNum) throws IOException {
        this(new ServerSocket(port), theMap, clientNum);
    }
    /**
     * @param clientNum is the number of clients
     */
    public Server(ServerSocket ss, Map theMap, int clientNum) {
        this.server = ss;
        this.theMap = theMap;
        this.mapView = new MapTextView(theMap);
        this.mapInfo = mapView.displayMap();
        this.clients = new ArrayList<ClientThread>();
        this.clientNum = clientNum;
        this.isListening = true;
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
        while(isListening)
            try {
                if(clients.size() < clientNum) connectOneClient();
            } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    public void connectOneClient() throws IOException{
        int index = clients.size();
        Socket clientSocket = server.accept();
        System.out.println("Client connected!");
        ClientThread clientThread = new ClientThread(clientSocket, colorList.get(index), mapInfo);
        clients.add(clientThread);
        clientThread.start();
    }
    /**
     * Stop the server
     * @throws IOException if input/output stream error
     */
    public void stop() throws IOException {
        isListening = false;
        // Close the server socket
        server.close();

        // Interrupt all client threads and remove them from the list
        for (ClientThread client : clients) {
            client.stopThread();
            client.interrupt();
        }
        clients.clear();
    }
    /**
     * This main method runs the server, listening on port 1651.
     * Specifically, it creates an instance and calls run.
     * When done from the command line, this program runs until
     * externally killed??
     * @param args is the command line arguments.  These are currently ignored.
     * @throws IOException if creation of the ServerSocket fails.

    public static void main(String[] args) throws IOException {
        Map m = new Game1Map();
        m.addTerritory(new BasicTerritory("Planto"));
        Server server =
                new Server(1651, m, 4);
        server.run();
    }
    */

}


