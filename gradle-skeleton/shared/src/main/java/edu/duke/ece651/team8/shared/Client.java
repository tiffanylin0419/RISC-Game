
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
            doOneOrder();
            reader.close();
            inputStream.close();
            socket.close();
        } catch (IOException e) {
            out.println(e.getMessage());
        }
    }

    /**
     * Receive the string info from the server into serverBuffer, NEED close the bufferReader after use this function
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
    public void send(String message) {
        output.println(message);
        output.println(END_OF_TURN);
        output.flush(); // flush the output buffer
    }

    /**
     * receive color string from server
     */
    public void receiveColor()throws  IOException{
        receive();
        color = buffer;
    }

    public void receiveMapInfo()throws  IOException{
        receive();
        mapInfo = buffer;
    }

    public void doInitialPlacement() throws IOException{
        receive();
        int placementTimes = Integer.parseInt(buffer);
        for(int i = 0; i < placementTimes;i++){
            while(true) {
                receive();
                while (true) {
                    try {
                        tryDoPlacementChoice(buffer, input);
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

    public void tryDoPlacementChoice(String prompt,BufferedReader input)throws Exception{
        out.print(prompt);
        String s = input.readLine();
        if(isNonNegativeInt(s)){
            send(s);
        }else{
            throw new IllegalArgumentException("Units number should be non_negative number");
        }
    }

    public boolean isNonNegativeInt(String number){
        return Integer.parseInt(number) >= 0;
    }

    public void doOneOrder()throws IOException{
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
                doOneMove(); //looks like the same as doOneMove  used to be doOneAttack here
            }else{
                break;
            }
        }
    }

    public String tryChooseOneAction(String prompt,BufferedReader input)throws IllegalArgumentException,IOException{
        out.print(prompt);
        String s = input.readLine();

        if(isValidChoice(s)){
            System.out.println("xxxxxx"+s + "xxxxxx");
            send(s);
            return s;
        }else{
            throw new IllegalArgumentException("Units number should be non_negative number");
        }
    }

    public boolean isValidChoice(String s){
        return s.equals("M")||s.equals("A")||s.equals("D");
    }

    public void doOneMove()throws IllegalArgumentException,IOException{
        receive();
        while (true) {
            try {
                trySendUnitReadNumber(buffer,input);
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
    public void trySendUnitReadNumber(String prompt,BufferedReader input)throws IllegalArgumentException,IOException{
        out.println(prompt);
        String s = input.readLine();
        if(isNonNegativeInt(s)){
            send(s);
        }else{
            throw new IllegalArgumentException("Units number should be non_negative number");
        }
    }

    public void trySendSourceTerritory(String prompt,BufferedReader input)throws IOException{
        out.println(prompt);
        String s = input.readLine();
        send(s);
    }

    public void trySendDestinationTerritory(String prompt,BufferedReader input)throws IOException{
        trySendSourceTerritory(prompt,input);
    }

    public void doOneAttack(){
        //to do
    }
    /**
     * Display map info
     */
    public void display() {
        out.println(color);
        displayMap();
    }
    public void displayMap() {
        out.println(mapInfo);
    }


}

