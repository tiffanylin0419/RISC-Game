package edu.duke.ece651.team8.shared;

import java.io.IOException;
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
    /** List of players*/
    private final ArrayList<Player> players;
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
    public Server(int port, Map theMap, int clientNum,ArrayList<Player> players) throws IOException {
        this(new ServerSocket(port), theMap, clientNum,players);
    }
    /**
     * @param clientNum is the number of clients
     */
    public Server(ServerSocket ss, Map theMap, int clientNum, ArrayList<Player> players) {
        this.server = ss;
        this.colorList = new ArrayList<>();
        this.players = players;
        for(int i = 0; i < clientNum; i++) {
            colorList.add(players.get(i).getColor());
        }
        this.theMap = theMap;
        this.mapView = new MapTextView();
        this.mapInfo = mapView.displayMap(theMap,players);
        this.clients = new ArrayList<>();
        this.clientNum = clientNum;
        this.isListening = true;
    }
    /**
     * @return the port of the server socket
     */
    public int getPort() {
        return server.getLocalPort();
    }

    /** Execute the server */
    public void run() {
        //add input player number
        while(isListening) {
            try {
                connectOneGame();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

    }
    public void connectOneGame() throws IOException{
        List<Socket> oneGameClients = new ArrayList<>();
        for(int i = 0; i < clientNum; i++) {
            Socket clientSocket = server.accept();
            oneGameClients.add(clientSocket);
            System.out.println("Client connected!");
        }
        ClientThread clientThread = new ClientThread(oneGameClients, colorList, mapInfo);
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
            client.interrupt();
        }
        clients.clear();
    }



}


