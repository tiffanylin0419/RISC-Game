package edu.duke.ece651.team8.shared;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server {
    /** server socket*/
    protected ServerSocket server;
    /** List of the client sockets */
    private final List<GameThread> games;
    /** number of clients */
    protected int clientNum;
    protected AbstractMapFactory factory;
    /** Boolean indicate whether the server is listening or not*/
    private boolean isListening;
    private HashMap<String, String> users;
    /**
     * Constructs a server with specified port
     *
     * @param port is the port of the socket
     */
    public Server(int port, int clientNum, AbstractMapFactory factory) throws IOException {
        this(new ServerSocket(port), clientNum, factory);
    }
    /**
     * @param clientNum is the number of clients
     */
    public Server(ServerSocket ss, int clientNum, AbstractMapFactory factory) {
        this.server = ss;
        this.games = new ArrayList<>();
        this.clientNum = clientNum;
        this.factory = factory;
        this.isListening = true;
        this.users = new HashMap<>();
    }
    /**
     * @return the port of the server socket
     */
    public int getPort() {
        return server.getLocalPort();
    }

    /** Execute the server */
    public void run() {
        //addAmount input player number
        while(isListening) {
            try {
//                Socket clientSocket = server.accept();
//                new Thread(new ClientLoginHandler(clientSocket, this)).start();
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

        GameThread gameThread = new GameThread(oneGameClients, factory);
        games.add(gameThread);
        gameThread.start();
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
        for (GameThread game : games) {
            game.interrupt();
        }
        games.clear();
    }
}


