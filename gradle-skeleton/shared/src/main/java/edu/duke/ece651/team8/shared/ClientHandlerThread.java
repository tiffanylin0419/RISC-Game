package edu.duke.ece651.team8.shared;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandlerThread extends Thread {
    private Socket clientSocket;
    private final Object lock = new Object();
    /** streams pass to client*/
    private PrintWriter output;
    /** InputStream from client */
    private InputStream inputStream;
    /** Reader for clients message*/
    private BufferedReader reader;
    /** Buffer for message from clients */
    protected String buffer;

    final String END_OF_TURN = "END_OF_TURN";
    private String mapInfo;
    /** Map of the game */
    private Map theMap;
    /** View of the map */
    protected View mapView;

    private final Player player;

    private final int placementTimes = 5;
    private final int unitAmount = 36;
    private String winnerName;
    private GameThread gameServer;

    /**
     * Constructor of the ClientHandlerThread
     * @param clientSocket
     * @param output
     * @param inputStream
     * @param reader
     * @param player
     * @param gameServer
     * @throws IOException
     */
    public ClientHandlerThread(Socket clientSocket, PrintWriter output, InputStream inputStream,
                               BufferedReader reader, Map theMap, Player player, GameThread gameServer) {
        this.clientSocket = clientSocket;
        this.output = output;
        this.inputStream = inputStream;
        this.reader = reader;
        this.theMap = theMap;
        this.player = player;
        this.mapView = new MapTextView();
        this.mapInfo = mapView.displayMap(theMap);
        this.winnerName = "";
        this.gameServer = gameServer;
    }
    @Override
    public void run() {
        try {
            sendInitialConfig();
            doInitialPlacement();
            while(this.winnerName == "") {//keep running if no one wins
                issueOrders();
                reportResult();
            }
        } finally {
            output.close();
        }
    }
    public void doSynchronization() {
        synchronized (gameServer) {
            try {
                gameServer.wait();
            } catch (InterruptedException e) {
                System.out.println("wait error");
            }
        }
    }
    public void sendInitialConfig() {
            //send color and initial map information to players
            send(player.getColor(), output);
            send(mapInfo,output);
            // wait for the server to finish processing messages
            doSynchronization();

    }

    /**
     * curr is the left units
     * size is the num of territory, and the index is the current round of placement
     * @param curr
     * @param index
     * @param size
     * @return true if unit num is >=1 or small enough so others can be at least 1
     */
    public boolean checkUnitNumValid(int curr, int index, int size) {
        int input = Integer.parseInt(buffer);
        // the left rounds
        int diff = size - index - 1;
        if (diff > (curr - input)) {
            throw new IllegalArgumentException("Army amount is not valid!");
        }
        return true;
    }
    private void setUnitInTerritory(Territory t) {
        int amount = Integer.parseInt(buffer);
        Army army = new BasicArmy(amount, t.getOwner());
        t.moveIn(army);
    }

    private void endPlacementPhase() {
        String prompt = "Placement phase is done!\n";
        mapInfo = mapView.displayMap(theMap);
        send(prompt,output);
        send(mapInfo,output);
    }

    /**
     * init placement of units
     */
    public void doInitialPlacement(){
        String num = Integer.toString(placementTimes);
        String prompt = "Please enter the units you would like to place in ";
        if(!player.isConnected()) interrupt(); //!!!
        send(num, output);
        placeUnitForTerritories(prompt, num);
        // wait for the server to finish processing messages
        doSynchronization();
        endPlacementPhase();
    }

    private void getWinner() {
        this.winnerName = theMap.getWinner();
    }

    /**
     * In initial placement, place unit for all territories
     * @param prompt is prompt of the step to print out
     */
    public void placeUnitForTerritories(String prompt, String num) {
        int curr = this.unitAmount;
        ArrayList<Territory> territories = player.getTerritories();
        int size = territories.size();
        for (int j = 0; j < size - 1; ++j) {
            Territory t = territories.get(j);
            send(prompt + t.getName() + " (" + curr + " units)\n", output);
            while (true) {

                if(!player.isConnected()) {
                    buffer = "1";
                    setUnitInTerritory(t);
                    break;
                }
                System.out.println("======="+t.getName()+"=======");

                try {
                    receive(reader);
                    checkUnitNumValid(curr, j, size);
                    setUnitInTerritory(t);
                    curr -= Integer.parseInt(buffer);
                    send("valid\n", output);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    send("invalid\n", output);
                } catch (IOException e) {
                    System.out.println(player.getColor() + " disconnect");
                    player.disconnect();
                    buffer = Integer.toString(curr - (size - j - 1));
                    setUnitInTerritory(t);
                    curr = 1;
                    break;
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                    send("invalid\n", output);
                }
            }
        }
        Army army = new BasicArmy(curr, territories.get(size - 1).getOwner());
        territories.get(size - 1).moveIn(army);
    }
    public void setWinner(String winner) {
        winnerName = winner;
    }
//    public Map getMap() {return theMap;}
    /**
     * Report result after each turn of the game
     * #1 send outcome
     * #2 send mapInfo
     * #3 send if the player loses
     * #4 send if there is a winner
     */
    public void reportResult() {
//        theMap = gameServer.getTheMap();
        theMap.doCombats();
        doSynchronization();
        String outcome = theMap.getOutcome();
        System.out.println("outcome:" + outcome);

        mapInfo = mapView.displayMap(theMap);
        send(outcome, output);
        send(mapInfo,output);
        if (player.isDefeated()) {
            String prompt = "lose";
            send(prompt, output);
        } else {
            String prompt = "continue";
            send(prompt, output);
        }
        getWinner();
        if (this.winnerName == "") {
            send("no winner", output);
        } else {
            send(this.winnerName, output);
        }
        doSynchronization();
    }
    /**
     * Issue orders (Move and Attack) for every client
     * if they are still alive
     */
    public void issueOrders() {
        try {
            if (!player.isConnected()) interrupt();
            if (!player.isDefeated()) {
                doOneCommit();
            }
        } catch (IOException e) {
            System.out.println(player.getColor() + " disconnect");
            player.disconnect();
            System.out.println(e.getMessage());
        }
        doSynchronization();

    }

    /**
     * Conduct one commit on server side
     * @throws IOException
     */
    public void doOneCommit() throws IOException {
        while(true) {
            String prompt = "You are the " + player.getColor() + " player, what would you like to do?\n(M)ove\n(A)ttack\n(D)one\n";
            send(prompt, output);
            receive(reader);
            if (buffer.equals("D")) {
                return;
            } else if (buffer.equals("M")) {
                doMoveOrder();
            } else if (buffer.equals("A")) {
                doAttackOrder();
            }
        }
    }

    /**
     * Conduct one command transmission between client and server
     * @param prompt is info to send to client
     * @throws IOException
     */
    public void doOneTransmission(String prompt) throws IOException {
        send(prompt, output);
        receive(reader);
        System.out.println("receive from client: "+buffer);
    }
    public String orderRuleCheck(Action ac) {
        String errorMessage=theMap.getChecker().checkAllRule(ac);
        if(errorMessage==null) {
            send("", output);
            ac.doAction();
        }
        else{
            send(errorMessage, output);
        }
        return errorMessage;
    }
    /**
     * Conduct move order with move message from client
     * @throws IOException
     */
    public String doMoveOrder() throws IOException{
        doOneTransmission("Please enter the number of units to move:");
        int num=-1;
        if(isPositiveInt(buffer)){
            num = Integer.parseInt(buffer);
        }
        doOneTransmission("Please enter the source territory:");
        String source = buffer;
        doOneTransmission("Please enter the destination territory:");
        String destination = buffer;
        Action ac = new MoveAction(player, source, destination, num, theMap);
        return orderRuleCheck(ac);
    }

    /**
     * Conduct attack order with attack message from client
     * @return current step attack action
     * @throws IOException
     */
    public String doAttackOrder() throws IOException{
        doOneTransmission("Please enter the number of units to attack:");
        int num=-1;
        if(isPositiveInt(buffer)){
            num = Integer.parseInt(buffer);
        }
        doOneTransmission("Please enter the source territory:");
        String source = buffer;
        doOneTransmission("Please enter the destination territory:");
        String destination = buffer;
        AttackAction ac = new AttackAction(player, source, destination, num, theMap); //Change move to attack
        return orderRuleCheck(ac);
    }
    /**
     * Send information to one client
     */
    public void send(String message, PrintWriter output) {
        output.println(message);
        output.println(END_OF_TURN);
        output.flush(); // flush the output buffer
    }

    /**
     * Receive message to buffer from the input reader
     * @param reader is the buffered reader of current client
     * @throws IOException
     */
    public void receive(BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        String ss = reader.readLine();
//        System.out.println(ss);
        sb.append(ss);
        String receLine = reader.readLine();
        if(receLine==null){
            throw new IOException("");
        }
        while(!receLine.equals(END_OF_TURN)) {   //!!!!
            sb.append("\n"+receLine);
            receLine = reader.readLine();
        }
        buffer = sb.toString();
    }

    /**
     * Determine if a string is a non-negative number string
     * @param number the string to be judged
     * @return true is >=0. Otherwise, false
     */
    public boolean isPositiveInt(String number){
        try{return Integer.parseInt(number) > 0;}
        catch(Exception e) {
            return false;
        }
    }

}
