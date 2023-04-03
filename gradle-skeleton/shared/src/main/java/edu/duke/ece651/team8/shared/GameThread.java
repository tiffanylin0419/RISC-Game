package edu.duke.ece651.team8.shared;


import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameThread extends Thread {
    private List<Socket> clientSockets;
    private List<ClientHandlerThread> clientThreads;
    private final Object lock = new Object();
    /** streams pass to client*/
    private List<PrintWriter> outputs;
    /** InputStream from clients */
    private List<InputStream> inputStreams;
    /** Reader for clients message*/
    private List<BufferedReader> readers;
    /** Buffer for message from clients */
    protected String buffer;

    final String END_OF_TURN = "END_OF_TURN";
    private String mapInfo;
    /** Map of the game */
    private final Map theMap;
    /** View of the map */
    protected View mapView;

    private final ArrayList<Player> players;

    private final int placementTimes = 5;
    private final int unitAmount = 36;
    private String winnerName;

    /**
     * Constructor of GameThread
     * @param clientSockets are the sockets of the game thread
     * @param factory is factory of the game
     */
    public GameThread(List<Socket> clientSockets, AbstractMapFactory factory) throws IOException{
        this.clientSockets = clientSockets;
        this.outputs = new ArrayList<>();
        this.inputStreams = new ArrayList<>();
        this.readers = new ArrayList<>();
        this.theMap = factory.createMap(clientSockets.size());
        this.players = factory.createPlayers(clientSockets.size(), theMap);
        this.mapView = new MapTextView();
        this.mapInfo = mapView.displayMap(theMap);
        for(Socket cs : clientSockets){
            outputs.add(new PrintWriter(cs.getOutputStream()));
            InputStream is= cs.getInputStream();
            inputStreams.add(is);
            readers.add(new BufferedReader(new InputStreamReader(is)));

        }
        this.winnerName = null;
        this.clientThreads = new ArrayList<>();
    }
    @Override
    public void run() {
        try {
            for (int i = 0; i < clientSockets.size(); i++) {
                ClientHandlerThread clientThread = new ClientHandlerThread(clientSockets.get(i), outputs.get(i),
                        inputStreams.get(i), readers.get(i), theMap, players.get(i), this);
                clientThreads.add(clientThread);
            }
            for (ClientHandlerThread t : clientThreads) {
                t.start();
            }
            while (true) {
                for (Thread t : clientThreads) {
                    while (t.getState() != Thread.State.WAITING) {
                    }
                }
//                theMap.doCombats();
                notifyClients();
            }
        } finally {
            for (PrintWriter output : outputs) {
                output.close();
            }
        }
    }
    public synchronized void notifyClients() {
        notifyAll(); // Notify all waiting threads
//        System.out.println("notify all");
    }

    public List<ClientHandlerThread> getClients() {
        return clientThreads;
    }
//
//    public void sendInitialConfig() {
//        for(int i = 0; i < clientSockets.size(); i++) {
//            //send color and initial map information to players
//            send(players.get(i).getColor(), outputs.get(i));
//            send(mapInfo,outputs.get(i));
//            //receive initial placements from players
//        }
//    }
//
//    /**
//     * curr is the left units
//     * size is the num of territory, and the index is the current round of placement
//     * @param curr
//     * @param index
//     * @param size
//     * @return true if unit num is >=1 or small enough so others can be at least 1
//     */
//    public boolean checkUnitNumValid(int curr, int index, int size) {
//        int input = Integer.parseInt(buffer);
//        // the left rounds
//        int diff = size - index - 1;
//        if (diff > (curr - input)) {
//            throw new IllegalArgumentException("Unit amount is not valid!");
//        }
//        return true;
//    }
//    private void setUnitInTerritory(Territory t) {
//        int amount = Integer.parseInt(buffer);
//        Unit unit = new BasicUnit(amount, t.getOwner());
//        t.moveIn(unit);
//    }
//
//    private void endPlacementPhase() {
//        String prompt = "Placement phase is done!\n";
//        mapInfo = mapView.displayMap(players);
//        for (int i = 0; i < clientSockets.size(); ++i) {
//            send(prompt,outputs.get(i));
//            send(mapInfo,outputs.get(i));
//        }
//    }
//
//    /**
//     * init placement of units
//     */
//    public void doInitialPlacement(){
//        String num = Integer.toString(placementTimes);
//        String prompt = "Please enter the units you would like to place in ";
//        for(int i = 0; i < clientSockets.size(); i++) {
//            if(!players.get(i).isConnected()) continue;
//            send(num, outputs.get(i));
//            placeUnitForTerritories(prompt, i);
//        }
//        endPlacementPhase();
//    }
//
//    private boolean hasWinner() {
//        for (int i = 0; i < clientSockets.size(); ++i) {
//            if (players.get(i).isWinner(theMap.getTerritories().size())) {
//                this.winnerName = players.get(i).getColor();
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * In initial placement, place unit for all territories
//     * @param prompt is prompt of the step to print out
//     * @param i is index of current client
//     */
//    public void placeUnitForTerritories(String prompt, int i) {
//        int curr = this.unitAmount;
//        ArrayList<Territory> territories = players.get(i).getTerritores();
//        int size = territories.size();
//        for (int j = 0; j < size - 1; ++j) {
//            while (true) {
//                Territory t = territories.get(j);
//                if(!players.get(i).isConnected()) {
//                    buffer = "1";
//                    setUnitInTerritory(t);
//                    break;
//                }
//                System.out.println("======="+t.getName()+"=======");
//                send(prompt + t.getName() + " (" + curr + " units)\n", outputs.get(i));
//                try {
//                    receive(readers.get(i));
//                    checkUnitNumValid(curr, j, size);
//                    setUnitInTerritory(t);
//                    curr -= Integer.parseInt(buffer);
//                    send("valid\n", outputs.get(i));
//                    break;
//                } catch (IllegalArgumentException e) {
//                    System.out.println(e.getMessage());
//                    send("invalid\n", outputs.get(i));
//                } catch (IOException e) {
//                    System.out.println(players.get(i).getColor() + " disconnect");
//                    players.get(i).disconnect();
//                    buffer = Integer.toString(curr - (size - j - 1));
//                    setUnitInTerritory(t);
//                    curr = 1;
//                    break;
//                }
//            }
//        }
//        Unit unit = new BasicUnit(curr, territories.get(size - 1).getOwner());
//        territories.get(size - 1).moveIn(unit);
//    }
//    public void setWinner(String winner) {
//        winnerName = winner;
//    }
//    /**
//     * Report result after each turn of the game
//     * #1 send outcome
//     * #2 send mapInfo
//     * #3 send if the player loses
//     * #4 send if there is a winner
//     */
//    public void reportResults() {
//        String outcome = theMap.doCombats();
//        hasWinner();
//        mapInfo = mapView.displayMap(players);
//        for (int i = 0; i < clientSockets.size(); i++) {
//            send(outcome, outputs.get(i));
//            send(mapInfo,outputs.get(i));
//            if (players.get(i).isDefeated()) {
//                String prompt = "lose";
//                send(prompt, outputs.get(i));
//            } else {
//                String prompt = "continue";
//                send(prompt, outputs.get(i));
//            }
//            if (this.winnerName == null) {
//                send("no winner", outputs.get(i));
//            } else {
//                send(this.winnerName, outputs.get(i));
//            }
//        }
//    }
//     /**
//     * Issue orders (Move and Attack) for every client
//      * if they are still alive
//     */
//    public void issueOrders() {
//        for (int i = 0; i < clientSockets.size(); i++) {
//            try {
//                if (!players.get(i).isConnected()) continue;
//                if (!players.get(i).isDefeated()) {
//                    doOneCommit(i);
//                }
//            } catch (IOException e) {
//                System.out.println(players.get(i).getColor() + " disconnect");
//                players.get(i).disconnect();
//                System.out.println(e.getMessage());
//            }
//        }
//    }
//
//    /**
//     * Conduct one commit on server side
//     * @param index is index of current client
//     * @throws IOException
//     */
//    public void doOneCommit(int index) throws IOException {
//        while(true) {
//            String prompt = "You are the " + players.get(index).getColor() + " player, what would you like to do?\n(M)ove\n(A)ttack\n(D)one\n";
//            send(prompt, outputs.get(index));
//            receive(readers.get(index));
//            if (buffer.equals("D")) {
//                return;
//            } else if (buffer.equals("M")) {
//                doMoveOrder(index);
//            } else if (buffer.equals("A")) {
//                doAttackOrder(index);
//            }
//        }
//    }
//
//    /**
//     * Conduct one command transmission between client and server
//     * @param index is index of current client
//     * @param prompt is info to send to client
//     * @throws IOException
//     */
//    public void doOneTransmission(int index, String prompt) throws IOException {
//        send(prompt, outputs.get(index));
//        receive(readers.get(index));
//        System.out.println("receive from client: "+buffer);
//    }
//    public String orderRuleCheck(Action ac, int index) {
//        String errorMessage=theMap.getChecker().checkAllRule(ac);
//        if(errorMessage==null) {
//            send("", outputs.get(index));
//            ac.doAction();
//        }
//        else{
//            send(errorMessage, outputs.get(index));
//        }
//        return errorMessage;
//    }
//    /**
//     * Conduct move order with move message from client
//     * @param index is index of current client
//     * @throws IOException
//     */
//    public String doMoveOrder(int index) throws IOException{
//        doOneTransmission(index, "Please enter the number of units to move:");
//        int num = Integer.parseInt(buffer);
//
//        doOneTransmission(index, "Please enter the source territory:");
//        String source = buffer;
//
//        doOneTransmission(index, "Please enter the destination territory:");
//        String destination = buffer;
//        Action ac = new MoveAction(players.get(index), source, destination, num, theMap);
//        return orderRuleCheck(ac, index);
//    }
//
//    /**
//     * Conduct attack order with attack message from client
//     * @param index is index of current client
//     * @return current step attack action
//     * @throws IOException
//     */
//    public String doAttackOrder(int index) throws IOException{
//        doOneTransmission(index, "Please enter the number of units to attack:");
//        int num = Integer.parseInt(buffer);
//
//        doOneTransmission(index, "Please enter the source territory:");
//        String source = buffer;
//
//        doOneTransmission(index, "Please enter the destination territory:");
//        String destination = buffer;
//        AttackAction ac = new AttackAction(players.get(index), source, destination, num, theMap); //Change move to attack
//        return orderRuleCheck(ac, index);
//    }
//    /**
//     * Send information to one client
//     */
//    public void send(String message, PrintWriter output) {
//        output.println(message);
//        output.println(END_OF_TURN);
//        output.flush(); // flush the output buffer
//    }
//
//    /**
//     * Receive message to buffer from the input reader
//     * @param reader is the buffered reader of current client
//     * @throws IOException
//     */
//    public void receive(BufferedReader reader) throws IOException {
//        StringBuilder sb = new StringBuilder();
//        String ss = reader.readLine();
////        System.out.println(ss);
//        sb.append(ss);
//        String receLine = reader.readLine();
//        if(receLine==null){
//            throw new IOException("");
//        }
//        while(!receLine.equals(END_OF_TURN)) {   //!!!!
//            sb.append("\n"+receLine);
//            receLine = reader.readLine();
//        }
//        buffer = sb.toString();
//    }

}
