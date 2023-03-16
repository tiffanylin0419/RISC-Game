package edu.duke.ece651.team8.shared;
import edu.duke.ece651.team8.shared.*;

import java.io.*;
import java.net.*;
/** Client pattern of the game*/
public class Client {
    /** Client socket for communicate with server */
    protected Socket socket;
    /** */
    InputStream inputStream;
    /***/
    BufferedReader reader;
    /** Buffer for message from server */
    protected String buffer;
    /** Output stream of the client*/
    protected PrintStream out;
    /** Input stream of the client*/
    protected BufferedReader input;
    /** info to be transfer */
    protected String mapInfo;
    /**client player color*/
    protected String color;

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
    }

    /** execute the client */
    public void run() {
        try {
            receiveColor();
            receiveMapInfo();
            display();
//            receive();
//            receive();
//            receive();
//            receive();
//            receiveMapInfo();
//            displayMap();
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
//    /**
//     * Receive the string info from the server
//     */
//    public void receive() throws IOException {
//        InputStream inputStream = socket.getInputStream();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//        StringBuilder sb = new StringBuilder();
//        color = reader.readLine();
//        String receLine = reader.readLine();
//        while(receLine != null) {
//            sb.append(receLine + "\n");
//            receLine = reader.readLine();
//        }
//        mapInfo = sb.toString();
//        reader.close();
//        inputStream.close();
//    }

    public void doInitialPlacement()throws IOException{
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
        String prompt = "please do your initial placement";
        tryDoPlacementChoice(prompt,input);
    }

    public static String tryDoPlacementChoice(String prompt,BufferedReader input)throws IOException{
        System.out.print(prompt);
        String s = input.readLine();
        //send(s);
        //receive(s);
        return s;
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

