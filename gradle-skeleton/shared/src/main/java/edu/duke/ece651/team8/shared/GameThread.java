package edu.duke.ece651.team8.shared;


import org.checkerframework.checker.units.qual.A;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameThread extends Thread {
    private List<ClientHandlerThread> clientThreads;
    private Object lock = new Object();
    /** streams pass to client*/
    private List<PrintWriter> outputs;
    /** Reader for clients message*/
    private List<BufferedReader> readers;
    /** Buffer for message from clients */
    protected String buffer;

    final String END_OF_TURN = "END_OF_TURN";
    private String mapInfo;
    /** Map of the game */
    private Map theMap;
    /** View of the map */
    protected View mapView;

    private ArrayList<Player> players;
    private List<PlayerAccount> accounts;

    private int placementTimes = 5;
    private int unitAmount = 36;
    private String winnerName;
    private int roomNumber;
    private boolean isStart;
    public GameThread(int playerNum, AbstractMapFactory factory, int roomNumber) {
        this.outputs = new ArrayList<>();
        this.readers = new ArrayList<>();
        this.theMap = factory.createMap(playerNum);
        this.players = factory.createPlayers(playerNum, theMap);
        this.mapView = new MapGuiView();
        this.mapInfo = mapView.displayMap(theMap);

        this.winnerName = null;
        this.clientThreads = new ArrayList<>();
        this.isStart = false;
        this.accounts = new ArrayList<>();
        this.roomNumber = roomNumber;
    }

    /**
     * Constructor of GameThread
     * @param clientSockets are the sockets of the game thread
     * @param factory is factory of the game
     */
    public GameThread(List<Socket> clientSockets, AbstractMapFactory factory, int roomNumber) throws IOException{
        this.outputs = new ArrayList<>();
        this.readers = new ArrayList<>();
        this.theMap = factory.createMap(clientSockets.size());
        this.players = factory.createPlayers(clientSockets.size(), theMap);
        this.mapView = new MapGuiView();
        this.mapInfo = mapView.displayMap(theMap);
        for(Socket cs : clientSockets){
            outputs.add(new PrintWriter(cs.getOutputStream()));
            InputStream is= cs.getInputStream();
            readers.add(new BufferedReader(new InputStreamReader(is)));

        }
        this.winnerName = null;
        this.clientThreads = new ArrayList<>();
        this.accounts = new ArrayList<>();
        this.roomNumber = roomNumber;
    }
    public int getPlayerNum() {
        return players.size();
    }
    public synchronized ClientHandlerThread backToGame(int index, PrintWriter out, BufferedReader in) {
        ClientHandlerThread cliTh = clientThreads.get(index);
        cliTh.reconnect(out, in);//haven't handle complete
//        cliTh.reconnect();
        System.out.println(players.get(index).getColor() + " reconnect");
        return cliTh;
    }
    public synchronized ClientHandlerThread join(PlayerAccount account){
        if(isStart) return null;
        PrintWriter out = account.getOutput();
        BufferedReader reader = account.getReader();
        outputs.add(out);
        readers.add(reader);
        ClientHandlerThread clientThread = new ClientHandlerThread(out,
                reader, theMap, players.get(outputs.size() - 1), this);
        clientThreads.add(clientThread);
        clientThread.start();
        System.out.println("current num" + outputs.size());
        if(outputs.size() == players.size()) {
            isStart = true;
            notify();
        }
        account.addJoinGame(this, outputs.size() - 1);
        accounts.add(account);
        return clientThread;
    }

    @Override
    public void run() {
        while(!isStart) {
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.out.println(" game thread wait error");
                }
            }
        }
        try {
            System.out.println("game thread start");
            while (true) {
                for (Thread t : clientThreads) {
                    while (t.getState() != Thread.State.WAITING) {
                    }
                }
                if(!checkFinish()) break;
                while(!checkConnection()) {}
                notifyClients();
            }
            System.out.println("game end!!!");
            //add stop thread
        } finally {
            for (int i = 0; i < accounts.size(); i++) {
                accounts.get(i).deleteEndGame(this, i);
            }
        }
    }
    @Override
    public boolean equals(Object other) {
        if (other != null && other.getClass().equals(getClass())) {
            GameThread otherGame = (GameThread) other;//
            return roomNumber == otherGame.getRoomNumber();
        }
        return false;

    }
    public int getRoomNumber() {
        return roomNumber;
    }
    public boolean checkFinish() {
        for(ClientHandlerThread t : clientThreads) {
            if(t.getStatus() != -1) return true;
        }
        return false;
    }
    public boolean checkConnection() {
        for(Player p : players) {
            if(p.isConnected()) return true;
        }
        return false;
    }
    public synchronized void notifyClients() {
        notifyAll(); // Notify all waiting threads
//        System.out.println("notify all");
    }

    public List<ClientHandlerThread> getClients() {
        return clientThreads;
    }
    public void shutDown() {
        for(ClientHandlerThread th : clientThreads) {
            th.status = -1;
        }
        for(PrintWriter out : outputs) {
            out.close();
        }
    }
}
