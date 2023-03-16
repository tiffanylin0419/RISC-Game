
package edu.duke.ece651.team8.shared;
import edu.duke.ece651.team8.shared.*;

import java.io.*;
import java.net.*;
/** Client pattern of the game*/
public class Client {
    /** Client socket for communicate with server */
    protected Socket socket;
    /** OutStream to server */
    PrintWriter output;
    /** InputStream from server */
    InputStream inputStream;
    /** Reader for server message*/
    BufferedReader reader;
    /** Buffer for message from server */
    protected String buffer;
    /** Output stream of the client*/
    protected PrintStream out;
    /** Input stream of the client, like terminal input*/
    protected BufferedReader input;
    /** info to be transfer */
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
    public Client(int port, String host) throws IOException {
        this(new Socket(host, port), System.out, System.in);
    }

    /**
     * @param out is the output stream of the client
     * @throws IOException
     */
    public Client(int port, String host, PrintStream out, InputStream in) throws IOException {
        this(new Socket(host, port), out,in);
    }
    public Client(Socket s, PrintStream out,InputStream in) throws IOException {
        this.socket = s;
        this.out = out;
        this.mapInfo = new String();
        this.color= new String();
        this.inputStream = socket.getInputStream();
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.input = new BufferedReader(new InputStreamReader(in));
        output = new PrintWriter(s.getOutputStream());
    }

    /** execute the client */
    public void run() {
        try {
            receiveColor();
            receiveMapInfo();
            display();
            doInitialPlacement();
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
            sb.append("\n"+receLine);
            receLine = reader.readLine();
        }
        buffer = sb.toString();
    }
    public void send(String message) throws IOException {
        output.println(message);
        output.print(END_OF_TURN);
        output.flush(); // flush the output buffer
    }

    /**
     * receive color string from server
     * @throws IOException
     */
    public void receiveColor()throws  IOException{
        receive();
        color = buffer;
    }

    public void receiveMapInfo()throws  IOException{
        receive();
        mapInfo = buffer;
    }

    public void doInitialPlacement()throws IOException{
        receive();
        int placementTimes = Integer.parseInt(buffer);
        for(int i = 0; i < placementTimes;i++){
            receive();
            while (true) {
                try {
                    tryDoPlacementChoice(buffer, input);
                } catch (Exception e) {
                    out.println(e.getMessage());
                    out.println("Please input an valid placement!");
                    continue;
                }
                break;
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

    boolean isNonNegativeInt(String number){
        if(Integer.parseInt(number) >= 0){
            return true;
        }
        return false;
    }

    /**
     * Display map info
     */
    public void display() {
        out.println(color);
        displayMap();
    }
    public void displayMap() {
        out.print(mapInfo);
    }


};

