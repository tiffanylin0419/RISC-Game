package edu.duke.ece651.team8.shared;


import org.checkerframework.checker.units.qual.A;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientThread extends Thread {
    private List<Socket> clientSockets;
    /** streams pass to client*/
    private List<PrintWriter> outputs;
    /** InputStream from clients */
    private List<InputStream> inputStreams;
    /** Reader for clients message*/
    private List<BufferedReader> readers;
    /** Buffer for message from clients */
    protected String buffer;
    /** Output stream of the client*/
    private List<String> colors;

    final String END_OF_TURN = "END_OF_TURN";
    private String mapInfo;
    /** Map of the game */
    private final Map theMap;
    /** View of the map */
    protected View mapView;
    private int clientNum;
    private final ArrayList<Player> players;

    private final int placementTimes = 5;
    private final int unitAmount = 36;

    /**
     * Constructor of ClientThread
     * @param clientSockets are the sockets of the game thread
     * @param factory is factory of the game
     */
    public ClientThread(List<Socket> clientSockets, AbstractMapFactory factory) throws IOException{
        this.clientSockets = clientSockets;
        this.clientNum = clientSockets.size();
        this.mapInfo = mapInfo;
        this.outputs = new ArrayList<>();
        this.inputStreams = new ArrayList<>();
        this.readers = new ArrayList<>();
        this.theMap = factory.createMap(clientSockets.size());
        this.players = factory.createPlayers(clientSockets.size(), theMap);
        this.mapView = new MapTextView();
        this.mapInfo = mapView.displayMap(players);
        this.colors = new ArrayList<>();
        for(int i = 0; i < clientNum; i++) {
            colors.add(players.get(i).getColor());
        }
        for(Socket cs : clientSockets){
            outputs.add(new PrintWriter(cs.getOutputStream()));
            InputStream is= cs.getInputStream();
            inputStreams.add(is);
            readers.add(new BufferedReader(new InputStreamReader(is)));

        }
    }
    @Override
    public void run() {
        try {
            sendInitialConfig();
            doInitialPlacement();
            issueOrders();
//            for(int i = 0; i < clientSockets.size(); i++) {
//                //send color and initial map information to players
//                mapInfo = mapView.displayMap(players);
//                send(mapInfo,outputs.get(i));
//                //receive initial placements from players
//            }
        } finally {
            for(PrintWriter output:outputs){
                output.close();
            }
        }
    }

    public void sendInitialConfig() {
        for(int i = 0; i < clientSockets.size(); i++) {
            //send color and initial map information to players
            send(colors.get(i), outputs.get(i));
            send(mapInfo,outputs.get(i));
            //receive initial placements from players
        }
    }


    public boolean checkUnitNumValid(int curr) {
        int input = Integer.parseInt(buffer);
        if (input > curr) {
            throw new IllegalArgumentException("Unit amount is not valid!");
        }
        return true;
    }
    private void setUnitInTerritory(Territory t) {
        int amount = Integer.parseInt(buffer);
        Unit unit = new BasicUnit(amount, t.getOwner());
        t.moveIn(unit);
    }

    private void endPlacementPhase() {
        String prompt = "Placement phase is done!\n";
        for (int i = 0; i < clientSockets.size(); ++i) {
            send(prompt, outputs.get(i));
        }
    }

    /**
     * init placement of units
     * @throws IOException
     */
    public void doInitialPlacement(){
        String num = Integer.toString(placementTimes);
        String prompt = "Please enter the units you would like to place in ";
        for(int i = 0; i < clientSockets.size(); i++) {
            send(num, outputs.get(i));
            int curr = this.unitAmount;
            ArrayList<Territory> territories = players.get(i).getTerritores();
            int size = territories.size();
            for (int j = 0; j < size - 1; ++j) {
                while (true) {
                    Territory t = territories.get(j);
                    send(prompt + t.getName() + "\n", outputs.get(i));
                    try {
                        receive(readers.get(i));
                        checkUnitNumValid(curr);
                        setUnitInTerritory(t);
                        curr -= Integer.parseInt(buffer);
                        send("valid\n", outputs.get(i));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        send("invalid\n", outputs.get(i));
                        continue;
                    }
                    break;
                }
            }
            Unit unit = new BasicUnit(curr, territories.get(size - 1).getOwner());
            territories.get(size - 1).moveIn(unit);
        }
        endPlacementPhase();
    }

     /**
     * Issue orders (Move and Attack) for every client
     * @throws IOException
     */
    public void issueOrders() {
        try {
            for (int i = 0; i < clientSockets.size(); i++) {
                String prompt = "You are the " + colors.get(i) + " player, what would you like to do?\n(M)ove\n(A)ttack\n(D)one";
                send(prompt, outputs.get(i));
                receive(readers.get(i));
                doOneCommit(i);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Conduct one commit on server side
     * @param index is index of current client
     * @throws IOException
     */
    public void doOneCommit(int index) throws IOException {
        List<AttackAction> aa = new ArrayList<>();
        while(!buffer.equals("D")) {
            if (buffer.equals("M")) {
                doMoveOrder(index);
            } else if (buffer.equals("A")) {
                aa.add(doAttackOrder(index));
            }
            receive(readers.get(index));
        }
        for(AttackAction a : aa) {
            a.doAction(theMap);
        }
    }

    /**
     * Conduct one command transmission between client and server
     * @param index is index of current client
     * @param prompt is info to send to client
     * @throws IOException
     */
    public void doOneTransmission(int index, String prompt) throws IOException {
        send(prompt, outputs.get(index));
        receive(readers.get(index));
        System.out.println("receive from client: "+buffer);
    }

    /**
     * Conduct move order with move message from client
     * @param index is index of current client
     * @throws IOException
     */
    public void doMoveOrder(int index) throws IOException{
        doOneTransmission(index, "Please enter the number of units to move:");
        int num = Integer.parseInt(buffer);

        doOneTransmission(index, "Please enter the source territory:");
        String source = buffer;

        doOneTransmission(index, "Please enter the destination territory:");
        String destination = buffer;
        Action ac = new MoveAction(players.get(index), source, destination, num, theMap);
        ac.doAction(theMap);
    }

    /**
     * Conduct attack order with attack message from client
     * @param index is index of current client
     * @return current step attack action
     * @throws IOException
     */
    public AttackAction doAttackOrder(int index) throws IOException{
        doOneTransmission(index, "Please enter the number of units to attack:");
        int num = Integer.parseInt(buffer);

        doOneTransmission(index, "Please enter the source territory:");
        String source = buffer;

        doOneTransmission(index, "Please enter the destination territory:");
        String destination = buffer;
        AttackAction ac = new AttackAction(players.get(index), source, destination, num, theMap); //Change move to attack
        return ac;
    }
    /**
     * Send infomation to one client
     */
    public void send(String message, PrintWriter output) {
        output.println(message);
        output.println(END_OF_TURN);
        output.flush(); // flush the output buffer
    }

    /**
     * Receive message to buffer from the input reader
     * @param reader is the bufferedreader of current client
     * @throws IOException
     */
    public void receive(BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        String ss = reader.readLine();
        System.out.println(ss);
        sb.append(ss);
        String receLine = reader.readLine();
//        System.out.println(receLine);
        while(!receLine.equals(END_OF_TURN)) {   //!!!!
            sb.append("\n"+receLine);
            receLine = reader.readLine();
        }
        buffer = sb.toString();
    }

}
