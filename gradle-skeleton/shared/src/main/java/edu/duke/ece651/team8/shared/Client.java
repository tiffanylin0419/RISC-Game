package edu.duke.ece651.team8.shared;
import edu.duke.ece651.team8.shared.*;

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
     */
    public Client(int port, String host) throws IOException {
        this(new Socket(host, port), System.out);
    }

    /**
     * @param out is the output stream of the client
     * @throws IOException
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

    /**
     * Display map info
     */
    public void display() {
        out.println(thePlayer.getColor());
        displayMap();
    }
    public void displayMap() {
        out.print(mapInfo);
    }
    /**
     * This main method runs the client, on localhost and port 1651.
     * Specifically, it creates an instance and calls run.
     * @param args is the command line arguments.  These are currently ignored.
     * @throws IOException if creation of the ServerSocket fails.

    public static void main(String[] args) throws IOException {
        String hostname = "localhost"; // set the hostname of the server
        int port = 1651; // set the port number
        Client client = new Client(port, hostname); // create a new client instance
        client.run(); // connect the client to the server
    }
    */

};

