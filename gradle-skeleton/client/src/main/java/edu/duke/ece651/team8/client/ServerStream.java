package edu.duke.ece651.team8.client;

import java.io.*;
import java.net.Socket;

public class ServerStream {

    protected Socket socket;
    /** OutStream to server */
    protected PrintWriter output;
    /** InputStreams from server */
    protected InputStream inputStream;
    /** Reader for server message*/
    protected BufferedReader reader;
    /** Buffer for message from server */
    protected String buffer;

    final String END_OF_TURN = "END_OF_TURN";

    public ServerStream(String hostname, int port)throws IOException {
        this(new Socket(hostname,port));
    }

    public ServerStream(Socket s)throws IOException {
        this(s,null,null,null);
        this.inputStream = s.getInputStream();
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.output = new PrintWriter(s.getOutputStream());
    }
    public ServerStream(BufferedReader reader, PrintWriter p, InputStream input) {
        this.reader = reader;
        this.output = p;
        this.inputStream = input;
    }

    public ServerStream(Socket s, PrintWriter p,  InputStream i, String b){
        this.socket = s;
        this.output = p;
        this.inputStream = i;
        this.buffer = b;
    }
    public void receive() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(reader.readLine());
        String receivedLine = reader.readLine();
        if(receivedLine==null){
            throw new IOException("");
        }
        while(!receivedLine.equals(END_OF_TURN)) {
            sb.append("\n").append(receivedLine);
            receivedLine = reader.readLine();
        }
        buffer = sb.toString();
    }
    public void send(String message) {
        output.println(message);
        output.println(END_OF_TURN);
        output.flush(); // flush the output buffer
    }
    public String read() throws IOException{
        receive();
        return buffer;
    }
    public void close()throws IOException{
        reader.close();
        inputStream.close();
        if(socket != null) socket.close();
    }
    public String getBuffer(){
        return buffer;
    }
}
