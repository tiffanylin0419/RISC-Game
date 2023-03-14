package edu.duke.ece651.team8.shared;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientThread extends Thread {
    private List<Socket> clientSockets;

    private List<PrintWriter> outputs;
    private List<String> colors;

    final String END_OF_TURN = "END_OF_TURN\n";
    private String mapInfo;

    /**
     * Constructor of ClientThread
     * @param clientSockets are the sockets of the game thread
     * @param colors is colorList assigned to these clients
     * @param mapInfo is map information
     */
    public ClientThread(List<Socket> clientSockets, List<String> colors, String mapInfo) throws IOException{
        this.clientSockets = clientSockets;
        this.colors = colors;
        this.mapInfo = mapInfo;
        this.outputs = new ArrayList<>();
        for(Socket cs : clientSockets){
            outputs.add(new PrintWriter(cs.getOutputStream()));
        }
    }
    @Override
    public void run() {
        try {
            for(int i = 0; i < clientSockets.size(); i++) {
                send(colors.get(i), outputs.get(i));
                send(mapInfo,outputs.get(i));

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }finally {
            for(PrintWriter output:outputs){
                output.close();
            }
        }
    }

    /**
     * Send infomation to one client
     */
    public void send(String message, PrintWriter output) throws IOException {
        output.println(message);
        output.print(END_OF_TURN);
        output.flush(); // flush the output buffer
    }



}
