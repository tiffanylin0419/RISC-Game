package edu.duke.ece651.team8.client;


import java.io.*;
import java.net.*;

/** Client pattern of the game*/
public class Client {
    protected ServerStream serverStream;
    protected PrintStream out;
    /** Input stream of the client, like terminal input*/
    protected BufferedReader input;
    /** info to be transferred, entered by user */
    protected String mapInfo;
    /**client player color*/
    protected String combatOutcome;
    protected String color;
    /** Delimiter*/
    final String END_OF_TURN = "END_OF_TURN";

    protected boolean isDefeated = false;

    protected String winner;
    /**
     * Constructs a server with specified port
     *
     * @param port is the port of the socket
     * @param host is the address of the server
     */
    public Client(int port, String host,BufferedReader in) throws IOException {
        this.serverStream=new ServerStream(host,port);
        this.out = System.out;
        this.input = in;
        this.winner = "no winner";
    }

    /** execute the client */
    public void run() {
        try {
            receiveColor();
            receiveMap();
            displayColor();
            displayMap();
            doInitialPlacement();
            receivePlacementResult();
            doAllTurns();
            serverStream.close();
        } catch (IOException e) {
            out.println(e.getMessage());
        }
    }
    public void receivePlacementResult() throws IOException{
        out.println(serverStream.read());
        receiveMap();
        displayMap();
    }
    public void doAllTurns() throws IOException {
        while(!isOver()) {//keep running if no one wins
            if(!isDefeated){
                doOneTurn();
            }
            reportResult();
//            System.out.println("outcome reach");
        }
    }
    public void reportResult() throws IOException{
        receiveCombatOutcome();
        displayCombatOutcome();
        receiveMap();
        displayMap();
        if(!isDefeated){
            receiveLoseStatus();
            //first time print lose information
            if (isDefeated){
                out.println("You lose.");
            }
        }else{
            receiveLoseStatus();
        }
        receiveWinner();
        if(isOver()){
            if(color.equals(winner)){
                out.println("Congratulations! You win!");
            }else {
                out.println(winner+" wins.");
            }
        }
    }
    /**
     * receive color string from server
     * @throws IOException if something wrong with receive
     */
    public void receiveColor()throws  IOException{
        color = serverStream.read();
    }

    /**
     * receive map information from server
     * @throws IOException if something wrong with receive
     */
    public void receiveMap()throws  IOException{
        mapInfo = serverStream.read();
    }
    public void receiveCombatOutcome()throws  IOException{
        combatOutcome = serverStream.read();
    }

    public void receiveWinner()throws  IOException{
        winner = serverStream.read();
    }

    public void receiveLoseStatus()throws IOException{
        if(serverStream.read().equals("lose")){
            isDefeated = true;
        }
    }



    /**
     * do initial placement phase, user need to input
     * the number she wants to place in a specific territory
     * if input is invalid, she needs to re-input
     * @throws IOException if something wrong with receive
     */
    public void doInitialPlacement() throws IOException{
        int placementTimes = Integer.parseInt(serverStream.read());
        for(int i = 0; i < placementTimes;i++){
            do {
                while (true) {
                    try {
                        tryInputUnitNumberToPlace(serverStream.read(), input);
                    } catch (Exception e) {
                        out.println(e.getMessage());
                        out.println("Please input a valid placement!");
                        continue;
                    }
                    break;
                }
                out.println(serverStream.read());
            } while (!serverStream.getBuffer().equals("valid\n"));
        }
    }

    /**
     * User input the unit number to place
     * @param prompt the prompt for placement
     * @param input the input buffer
     * @throws Exception if something wrong with receive
     */
    public void tryInputUnitNumberToPlace(String prompt, BufferedReader input)throws Exception{
        out.print(prompt);
        String s = input.readLine();
        if(isPositiveInt(s)){
            serverStream.send(s);
        }else{
            throw new IllegalArgumentException("Units number should be non_negative number");
        }
    }

    /**
     * Determine if a string is a non-negative number string
     * @param number the string to be judged
     * @return true is >=0. Otherwise, false
     */
    public boolean isPositiveInt(String number){
        return Integer.parseInt(number) > 0;
    }

    /**
     * Player do her turn
     * @throws IOException if something wrong with receive
     */
    public void doOneTurn()throws IOException{
        String choice;
        label:
        while(true) {
            while (true) {
                try {
                    choice = tryChooseOneAction(serverStream.read(), input);
                } catch (IllegalArgumentException e) {
                    out.println(e.getMessage());
                    System.out.println("Please input an valid action choice");
                    continue;
                }
                break;
            }
//            System.out.println("===========" + choice + "===========");
            switch (choice) {
                case "M":
                    if (doOneMove().equals("")) {
                        System.out.println("Successfully moved\n");
                        continue;
                    }
                    break;
                case "A":
                    if (doOneAttack().equals("")) {
                        System.out.println("Successfully attacked\n");
                        continue;
                    }
                    break;
                case "D":
                    break label;
            }
            System.out.println("Action failed\n");
        }
    }

    /**
     * player choose one action order to do
     * @param prompt to prompt for action choices
     * @param input the input buffer
     * @return input choice string
     * @throws IllegalArgumentException illegal input choice
     * @throws IOException if something wrong with receive
     */
    public String tryChooseOneAction(String prompt,BufferedReader input)throws IllegalArgumentException,IOException{
        out.print(prompt);
        String s = input.readLine();
        if(isValidChoice(s)){
//            System.out.println("xxxxxx"+s + "xxxxxx");
            serverStream.send(s);
            return s;
        }else{
            throw new IllegalArgumentException("Action should be \"M\"(move) \"A\"(attack) or \"D\"(done)");
        }
    }

    /**
     * Determine if a string is a valid choice string
     * @param s the string to be judged
     * @return true valid. Otherwise, false
     */
    public boolean isValidChoice(String s){
        return s.equals("M")||s.equals("A")||s.equals("D");
    }

    /**
     * Player do move phase
     * @throws IOException if something wrong with receive
     */
    public String doOneMove()throws IOException{
        while (true) {
            try {
                trySendUnitNumber(serverStream.read(),input);
            } catch (IllegalArgumentException e) {
                out.println(e.getMessage());
                continue;
            }
            break;
        }
        trySendSourceTerritory(serverStream.read(),input);
        trySendDestinationTerritory(serverStream.read(),input);
        if(!serverStream.read().equals("")){
            out.println(serverStream.getBuffer());
        }
        return serverStream.getBuffer();
    }

    /**
     * try to send a valid unit number
     * @param prompt the prompt for input
     * @param input input buffer
     * @throws IllegalArgumentException invalid unit number input
     * @throws IOException if something wrong with receive
     */
    public void trySendUnitNumber(String prompt,BufferedReader input)throws IllegalArgumentException,IOException{
        out.println(prompt);
        String s = input.readLine();
        if(isPositiveInt(s)){
            serverStream.send(s);
        }else{
            throw new IllegalArgumentException("Units number should be non_negative number");
        }
    }

    /**
     * try to send source territory
     * @param prompt the prompt for input
     * @param input input buffer
     * @throws IOException if something wrong with receive
     */
    public void trySendSourceTerritory(String prompt,BufferedReader input)throws IOException{
        out.println(prompt);
        String s = input.readLine();
        serverStream.send(s);
    }

    /**
     * try to send destination territory
     * @param prompt the prompt for input
     * @param input input buffer
     * @throws IOException if something wrong with receive
     */
    public void trySendDestinationTerritory(String prompt,BufferedReader input)throws IOException{
        trySendSourceTerritory(prompt,input);
    }

    /**
     * user do attack phase
     * @throws IOException if something wrong with receive
     * @return null if not actually conduct
     */
    public String doOneAttack()throws IOException{
        //to do
        return doOneMove();
    }
    /**
     * Display map info
     */
    public void displayColor() {
        out.println(color);
    }

    /**
     * Display map to out
     */
    public void displayMap() {
        out.println(mapInfo);
    }

    public void displayCombatOutcome(){
        out.println(combatOutcome);
    }

    public boolean isOver(){
        return !winner.equals("no winner");
    }

}

