
package edu.duke.ece651.team8.shared;

import java.io.*;
import java.net.*;
/** Client pattern of the game*/
public class Client {
    /** Client socket for communicate with server */
    protected Socket socket;
    /** OutStream to server */
    protected PrintWriter output;
    /** InputStreams from server */
    protected InputStream inputStream;
    /** Reader for server message*/
    protected BufferedReader reader;
    /** Buffer for message from server */
    protected String buffer;
    /** Output stream of the client*/
    protected PrintStream out;
    /** Input stream of the client, like terminal input*/
    protected BufferedReader input;
    /** info to be transferred, entered by user */
    protected String mapInfo;
    /**client player color*/
    protected String color;
    /** Delimiter*/
    final String END_OF_TURN = "END_OF_TURN";
    /**
     * Constructs a server with specified port
     *
     * @param port is the port of the socket
     * @param host is the address of the server
     */
    public Client(int port, String host,BufferedReader in) throws IOException {
        this(new Socket(host, port), System.out, in);
    }

    /**
     * @param out is the output stream of the client
     */
    public Client(int port, String host, PrintStream out, BufferedReader in) throws IOException {
        this(new Socket(host, port), out,in);
    }
    public Client(Socket s, PrintStream out,BufferedReader in) throws IOException {
        this.socket = s;
        this.out = out;
        this.inputStream = socket.getInputStream();
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.input = in;
        output = new PrintWriter(s.getOutputStream());
    }

    /** execute the client */
    public void run() {
        try {
            receiveColor();
            receiveMapInfo();
            display();
            doInitialPlacement();
            doOneTurn();
            reader.close();
            inputStream.close();
            socket.close();
        } catch (IOException e) {
            out.println(e.getMessage());
        }
    }

    /**
     * Receive the string info from the server into serverBuffer
     */
    public void receive() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(reader.readLine());
        String receLine = reader.readLine();
        while(!receLine.equals(END_OF_TURN)) {
            sb.append("\n").append(receLine);
            receLine = reader.readLine();
        }
        buffer = sb.toString();
    }

    /**
     * Send one message to server
     * @param message the message to send
     */
    public void send(String message) {
        output.println(message);
        output.println(END_OF_TURN);
        output.flush(); // flush the output buffer
    }

    /**
     * receive color string from server
     * @throws IOException if something wrong with receive
     */
    public void receiveColor()throws  IOException{
        receive();
        color = buffer;
    }

    /**
     * receive map information from server
     * @throws IOException if something wrong with receive
     */
    public void receiveMapInfo()throws  IOException{
        receive();
        mapInfo = buffer;
    }

    /**
     * do initial placement phase, user need to input
     * the number she wants to place in a specific territory
     * if input is invalid, she needs to re-input
     * @throws IOException if something wrong with receive
     */
    public void doInitialPlacement() throws IOException{
        receive();
        int placementTimes = Integer.parseInt(buffer);
        for(int i = 0; i < placementTimes;i++){
            while(true) {
                receive();
                while (true) {
                    try {
                        tryInputUnitNumberToPlace(buffer, input);
                    } catch (Exception e) {
                        out.println(e.getMessage());
                        out.println("Please input a valid placement!");
                        continue;
                    }
                    break;
                }
                receive();
                out.println(buffer);
                if (buffer.equals("valid\n")) {
                    break;
                }
            }
        }
        receive();
        out.print(buffer);
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
        if(isNonNegativeInt(s)){
            send(s);
        }else{
            throw new IllegalArgumentException("Units number should be non_negative number");
        }
    }

    /**
     * Determine if a string is a non-negative number string
     * @param number the string to be judged
     * @return true is >=0. Otherwise, false
     */
    public boolean isNonNegativeInt(String number){
        return Integer.parseInt(number) >= 0;
    }

    /**
     * Player do her turn
     * @throws IOException if something wrong with receive
     */
    public void doOneTurn()throws IOException{
        receive();
        String choice;
        while(true) {
            while (true) {
                try {

                    choice = tryChooseOneAction(buffer, input);
                } catch (IllegalArgumentException e) {
                    out.println(e.getMessage());
                    System.out.println("Please input an valid action choice");
                    continue;
                }
                break;
            }
            System.out.println("===========" + choice + "===========");
            if (choice.equals("M")) {
                doOneMove();
            } else if (choice.equals("A")) {
                doOneAttack();
            }else{
                break;
            }
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
            System.out.println("xxxxxx"+s + "xxxxxx");
            send(s);
            return s;
        }else{
            throw new IllegalArgumentException("Action should be \"M\"(move) \"A\"(attack) or \"D\"(done) ");
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
    public void doOneMove()throws IOException{
        receive();
        while (true) {
            try {
                trySendUnitNumber(buffer,input);
            } catch (IllegalArgumentException e) {
                out.println(e.getMessage());
                out.println("Please input a valid unit number!");
                continue;
            }
            break;
        }
        receive();
        trySendSourceTerritory(buffer,input);
        receive();
        trySendDestinationTerritory(buffer,input);
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
        if(isNonNegativeInt(s)){
            send(s);
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
        send(s);
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
     */
    public void doOneAttack()throws IOException{
        //to do
        doOneMove();
    }
    /**
     * Display map info
     */
    public void display() {
        out.println(color);
        displayMap();
    }

    /**
     * Display map to out
     */
    public void displayMap() {
        out.println(mapInfo);
    }


}

