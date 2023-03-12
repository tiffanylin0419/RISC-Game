package edu.duke.ece651.team8.shared;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientThread extends Thread {
    private List<Socket> clientSockets;
    private List<String> colors;
    private String mapInfo;

    /**
     * Constructor of ClientThread
     * @param clientSockets are the sockets of the game thread
     * @param colors is colorList assigned to these clients
     * @param mapInfo is map information
     */
    public ClientThread(List<Socket> clientSockets, List<String> colors, String mapInfo){
        this.clientSockets = clientSockets;
        this.colors = colors;
        this.mapInfo = mapInfo;
    }
    @Override
    public void run() {
        try {
            for(int i = 0; i < clientSockets.size(); i++) {
                send(colors.get(i), clientSockets.get(i));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Send infomation to one client
     * @param color is the color assigned to the client
     * @param client is the socket of the client
     */
    public void send(String color, Socket client) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter output = new PrintWriter(client.getOutputStream(), true);
        output.println(color);
        output.println(mapInfo);
        output.close();
    }


}
