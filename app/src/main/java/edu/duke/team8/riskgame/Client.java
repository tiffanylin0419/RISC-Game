package edu.duke.team8.riskgame;

import java.io.*;
import java.net.*;
/** Client pattern of the game*/
public class Client {
    /** Client socket */
    protected Socket socket;
    /** info to be transfer */
    protected String mapInfo;
    /** Output stream of the client*/
    protected PrintStream out;
    /** Player owned by the client */
    protected Player thePlayer;
    /**
     * Constructs a server with specified port
     *
     * @param port is the port of the socket
     * @param host is the address of the server
     * @param out is the output stream of the client
     */
    public Client(int port, String host, PrintStream out) throws IOException {
        this(new Socket(host, port), out);
    }
    public Client(Socket s, PrintStream out) throws IOException {
        this.socket = s;
        this.out = out;
        this.thePlayer = new TextPlayer("unassigned");
        this.mapInfo = new String();
    }

    /** execute the client */
    public void run() {
        try {
            receive();
            display();
            socket.close();
        } catch (IOException e) {
            out.println("Out/Input stream error");
        }
    }
    /**
     * Send the string info through the socket
     * @param socket is socket used to transfer
     * @param info is the message to be transfer

    public void sendString(Socket socket, String info) throws IOException {
    OutputStream outputStream = socket.getOutputStream();
    PrintWriter printWriter = new PrintWriter(outputStream, true);

    printWriter.println(info);

    printWriter.close();
    outputStream.close();
    }
     */

    /**
     * Receive the string info from the server
     */
    public void receive() throws IOException {
        InputStream inputStream = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String color = reader.readLine();
        thePlayer.setColor(color);
        String receLine = reader.readLine();
        while(receLine != null) {
            sb.append(receLine + "\n");
            receLine = reader.readLine();
        }
        mapInfo = sb.toString();
        reader.close();
        inputStream.close();
    }
    public void display() {
        out.println(thePlayer.getColor());
        displayMap();
    }
    public void displayMap() {
        out.print(mapInfo);
    }
};

