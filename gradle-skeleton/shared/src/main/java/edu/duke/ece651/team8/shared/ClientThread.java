package edu.duke.ece651.team8.shared;


import org.checkerframework.checker.units.qual.A;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientThread extends Thread {
    private List<Socket> clientSockets;
    /** streams pass to client*/
    private List<PrintWriter> outputs;
    /** InputStream from clients */
    private List<InputStream> inputStreams;
    /** Reader for clients message*/
    private List<BufferedReader> readers;
    /** Buffer for message from clients */
    protected String buffer;
    /** Output stream of the client*/
    private List<String> colors;

    final String END_OF_TURN = "END_OF_TURN\n";
    private String mapInfo;
    /** Map of the game */
    private final Map theMap;
    /** View of the map */
    protected View mapView;
    private int clientNum;
    private final ArrayList<Player> players;

    private final int placementTimes = 5;
    private final int unitAmount = 36;

    /**
     * Constructor of ClientThread
     * @param clientSockets are the sockets of the game thread
     * @param factory is factory of the game
     */
    public ClientThread(List<Socket> clientSockets, AbstractMapFactory factory) throws IOException{
        this.clientSockets = clientSockets;
        this.clientNum = clientSockets.size();
        this.mapInfo = mapInfo;
        this.outputs = new ArrayList<>();
        this.inputStreams = new ArrayList<>();
        this.readers = new ArrayList<>();
        this.theMap = factory.createMap(clientSockets.size());
        this.players = factory.createPlayers(clientSockets.size(), theMap);
        this.mapView = new MapTextView();
        this.mapInfo = mapView.displayMap(players);
        this.colors = new ArrayList<>();
        for(int i = 0; i < clientNum; i++) {
            colors.add(players.get(i).getColor());
        }
        for(Socket cs : clientSockets){
            outputs.add(new PrintWriter(cs.getOutputStream()));
            InputStream is= cs.getInputStream();
            inputStreams.add(is);
            readers.add(new BufferedReader(new InputStreamReader(is)));

        }
    }
    @Override
    public void run() {
        try {
            sendInitialConfig();
            issueOrders();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }finally {
            for(PrintWriter output:outputs){
                output.close();
            }
        }
    }

    public void sendInitialConfig()throws IOException{
        for(int i = 0; i < clientSockets.size(); i++) {
            //send color and initial map information to players
            send(colors.get(i), outputs.get(i));
            send(mapInfo,outputs.get(i));
            //receive initial placements from players
        }
    }

    /**
     * Issue orders (Move and Attack) for every client
     * @throws IOException
     */
    public void issueOrders() throws IOException{
        for(int i = 0; i < clientSockets.size(); i++) {
            String prompt = "You are the " + colors.get(i) + " player, what would you like to do?";
            send(prompt, outputs.get(i));
            receive(readers.get(i));
            if (buffer.equals("M")) {
                doMoveOrder(i);
            }
        }
    }
    public void doOneTransmission(int index, String prompt) throws IOException {
        send(prompt, outputs.get(index));
        receive(readers.get(index));
    }

    /**
     * Conduct move order with move message from client
     * @param index is index of current client
     * @throws IOException
     */
    public void doMoveOrder(int index) throws IOException{
        doOneTransmission(index, "Please enter the number of units to move:");
        int num = Integer.parseInt(buffer);

        doOneTransmission(index, "Please enter the source territory:");
        String source = buffer;

        doOneTransmission(index, "Please enter the destination territory:");
        String destination = buffer;
        Action ac = new MoveAction(players.get(index), source, destination, num, theMap);
        ac.doAction(theMap);
    }
    /**
     * Send infomation to one client
     */
    public void send(String message, PrintWriter output) throws IOException {
        output.println(message);
        output.print(END_OF_TURN);
        output.flush(); // flush the output buffer
    }
    public void receive(BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(reader.readLine());
        String receLine = reader.readLine();
        while(!receLine.equals("END_OF_TURN")) {   //!!!!
            sb.append("\n"+receLine);
            receLine = reader.readLine();
        }
        buffer = sb.toString();
    }

}
