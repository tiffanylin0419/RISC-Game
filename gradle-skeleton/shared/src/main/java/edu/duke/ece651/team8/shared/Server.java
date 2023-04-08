package edu.duke.ece651.team8.shared;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
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
    private List<PlayerAccount> accounts;
    final String END_OF_TURN = "END_OF_TURN";
    /**
     * Constructs a server with specified port
     *
     * @param port is the port of the socket
     */
    public Server(int port, AbstractMapFactory factory) throws IOException {
        this(new ServerSocket(port), factory);
    }
    /**
     */
    public Server(ServerSocket ss, AbstractMapFactory factory) {
        this.server = ss;
        this.games = new ArrayList<>();
        this.factory = factory;
        this.isListening = true;
        this.accounts = new ArrayList<>();
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
                Socket clientSocket = server.accept();
                new Thread(() -> handlePlayerConnection(clientSocket)).start();
//                connectOneGame();
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
    public PlayerAccount processSignup(PrintWriter out, BufferedReader reader) throws IOException {
        String prompt = "Create username";
        send(prompt, out);
        String username = receive(reader);
        String passwordPrompt = "Create password";
        send(passwordPrompt , out);
        String password = receive(reader);
        PlayerAccount account = new PlayerAccount(out, reader, username, password);
        return account;
    }
    public PlayerAccount processLogin(PrintWriter out, BufferedReader reader) throws IOException {
        String prompt = "Enter username";
        send(prompt, out);
        String username = receive(reader);
        String passwordPrompt = "Enter password";
        send(passwordPrompt , out);
        String password = receive(reader);
        PlayerAccount account = searchForAccount(username, password);
        if(account == null) {
            send("Username or password not correct", out);
            processLoginAndSignup(out, reader); //change later
        }
        account.updateIO(out, reader);
        return account;
    }
    public PlayerAccount processLoginAndSignup(PrintWriter out, BufferedReader reader) throws IOException{
        String prompt = "Login or Signup: L/S";
        send(prompt, out);
        String res = receive(reader);
        if(res.equals("L")) {
            return processLogin(out, reader);
        } else { //only two buttons??
            return processSignup(out, reader);
        }
    }
    public PlayerAccount searchForAccount(String username, String password) {
        for(PlayerAccount ac: accounts) {
            if(ac.match(username, password)) return ac;
        }
        return null;
    }
    public GameThread findMatch(PlayerAccount account) throws IOException{
        String prompt = "Join your exist game? Y/N"; // haven't dealt with Y
        send(prompt, account.getOutput());
        String res = receive(account.getReader());
        if(res.equals("N")) {
            String joinPrompt = "Enter how many players' game you want to join"; // haven't dealt with Y
            send(joinPrompt, account.getOutput());
            int num = Integer.parseInt(receive(account.getReader()));
            return joinGame(num, account);
        }
        return null;
    }
    public synchronized GameThread joinGame(int num, PlayerAccount account){
        System.out.println("games size" + games.size());
        for(GameThread game : games) {
            if(game.getPlayerNum() == num && game.join(account)) {
                return game;
            }
        }
        GameThread gameThread = new GameThread(num, factory);
        gameThread.start();
        gameThread.join(account);
        games.add(gameThread);
        return gameThread;
    }
    public void send(String message, PrintWriter output) {
        output.println(message);
        output.println(END_OF_TURN);
        output.flush(); // flush the output buffer
    }

    public String receive(BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        String ss = reader.readLine();
//        System.out.println(ss);
        sb.append(ss);
        String receLine = reader.readLine();
        if(receLine==null){
            throw new IOException("");
        }
        if(!receLine.equals(END_OF_TURN)) {
            throw new IOException("can not enter more");
        }
        return sb.toString();
    }
    private void handlePlayerConnection(Socket socket) {
        System.out.println("Client connected!");
        PlayerAccount account;
        try {
            PrintWriter output = new PrintWriter(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(output);
            account = processLoginAndSignup(output, reader);
            while (true) {
                GameThread game = findMatch(account);
                while(game.isAlive()) {}
            }
        } catch(IOException e) {
            System.out.println("handlePlayerConnection IOException");
        }
    }
}


