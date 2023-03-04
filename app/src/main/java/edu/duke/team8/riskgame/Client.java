package edu.duke.team8.riskgame;

import java.io.*;
import java.net.*;
/** Client pattern of the game*/
public class Client {
    /** Client socket */
    protected Socket socket;
    /** info to be transfer */
    protected String info;
    /** Output stream of the client*/
    protected PrintStream out;
    /**
     * Constructs a server with specified port
     *
     * @param port is the port of the socket
     * @param host is the address of the server
     * @param out is the output stream of the client
     */
    public Client(int port, String host, PrintStream out) throws IOException {
        this.socket = new Socket(host, port);
        this.out = out;
    }
    public Client(Socket s, PrintStream out) throws IOException {
        this.socket = s;
        this.out = out;
    }

    /** execute the client */
    public void run() {
        try {

            info = receive();
            out.print(info);
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
     * @return string of the received info
     */
    public String receive() throws IOException {
        InputStream inputStream = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String receLine = reader.readLine();
        while(receLine != null) {
            sb.append(receLine + "\n");
            receLine = reader.readLine();
        }

        reader.close();
        inputStream.close();

        return sb.toString();
    }
};

