//package edu.duke.ece651.team8.client;
//
//import javafx.application.Platform;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.StackPane;
//import javafx.scene.control.TextInputDialog;
//import java.util.Optional;
//
//import java.io.*;
//import java.net.Socket;
//
//public class ClientGUI {
//    /** Client socket for communicate with server */
//    protected Socket socket;
//    /** OutStream to server */
//    protected PrintWriter output;
//    /** InputStreams from server */
//    protected InputStream inputStream;
//    /** Reader for server message*/
//    protected BufferedReader reader;
//    /** Buffer for message from server */
//    protected String buffer;
//    /** Output stream of the client*/
//    protected PrintStream out;
//    /** Input stream of the client, like terminal input*/
//    protected BufferedReader input;
//    /** info to be transferred, entered by user */
//    protected String mapInfo;
//    /**client player color*/
//    protected String combatOutcome;
//    protected String color;
//    /** Delimiter*/
//    final String END_OF_TURN = "END_OF_TURN";
//
//    protected boolean isDefeated = false;
//
//    protected String winner;
//
//    protected GUI gui;
//    protected boolean guiInput=false;
//    /**
//     * Constructs a server with specified port
//     *
//     * @param port is the port of the socket
//     * @param host is the address of the server
//     */
//    public ClientGUI(int port, String host,BufferedReader in, GUI gui) throws IOException {
//        this.socket = new Socket(host, port);
//        this.out= System.out;
//        this.inputStream = this.socket.getInputStream();
//        this.reader = new BufferedReader(new InputStreamReader(inputStream));
//        this.output = new PrintWriter(this.socket.getOutputStream());
//        this.input = in;
//        this.winner = "no winner";
//        this.gui=gui;
//    }
//
//    public void reloadGameScene(){
//        Platform.runLater(() -> {
//            gui.GameScene();
//        });
//    }
//    public void setMessage(String message){
//        Platform.runLater(() -> {
//            gui.message.setText(message);
//        });
//    }
//
//    public void setError(String error){
//        Platform.runLater(() -> {
//            gui.error.setText(error);
//        });
//    }
//    public void setColor(String color){
//        Platform.runLater(() -> {
//            gui.color.setText(gui.color.getText()+color);
//        });
//    }
//    public void addInput(){
//        Platform.runLater(() -> {
//            TextInputDialog dialog = new TextInputDialog();
//            dialog.setTitle("Input Dialog");
//            dialog.setHeaderText("Please enter a value:");
//            Optional<String> result = dialog.showAndWait();
//
//            Thread.currentThread().setUncaughtExceptionHandler((thread, throwable) -> {
//                addInput();
//                setError(throwable.getMessage());
//            });
//
//            if(result.isPresent()) {
//                if(isPositiveInt(result.get())){
//                    send(result.get());
//                }
//                else{
//                    throw new IllegalArgumentException("unit number must > 0");
//                }
//
//            }
//        });
//    }
//
//    /** execute the client */
//    public void run() {
//        try {
//            receiveColor();
//            receiveMap();
//            displayColor();
//            displayMap();
//
//            doInitialPlacement();
//            /*receivePlacementResult();
//            doAllTurns();
//            reader.close();
//            inputStream.close();
//            socket.close();*/
//        } catch (IOException e) {
//            out.println(e.getMessage());
//        }
//    }
//
//    /**
//     * receive color string from server
//     * @throws IOException if something wrong with receive
//     */
//    public void receiveColor()throws  IOException{
//        receive();
//        color = buffer;
//    }
//    /**
//     * receive map information from server
//     * @throws IOException if something wrong with receive
//     */
//    public void receiveMap()throws  IOException{
//        receive();
//        mapInfo = buffer;
//        //should receive
//        // owner of every territory
//        // unit and resource info
//        // player info ex. level
//    }
//    /**
//     * Display map info
//     */
//    public void displayColor() {
//        setColor(color);
//    }
//    /**
//     * Display map to out
//     */
//    public void displayMap() {
//        //display mapInfo
//        //color of every territory
//        //
//        //reloadGameScene()
//    }
//    /**
//     * do initial placement phase, user need to input
//     * the number she wants to place in a specific territory
//     * if input is invalid, she needs to re-input
//     * @throws IOException if something wrong with receive
//     */
//    public void doInitialPlacement() throws IOException{
//        receive();
//        int placementTimes = Integer.parseInt(buffer);
//        for(int i = 0; i < placementTimes;i++){
//            do {
//                receive();
//                setMessage(buffer);
//                addInput();
//                receive();
//                setError(buffer);
//            } while (!buffer.equals("valid\n"));
//            setMessage("Placement Done");
//        }
//    }
//    public void receivePlacementResult() throws IOException{
//        receive();
//        out.println(buffer);
//        receiveMap();
//        displayMap();
//
//    }
//    public void doAllTurns() throws IOException {
//        while(!isOver()) {//keep running if no one wins
//            if(!isDefeated){
//                doOneTurn();
//            }
//            reportResult();
//        }
//    }
//    public void reportResult() throws IOException{
//        receiveCombatOutcome();
//        displayCombatOutcome();
//        receiveMap();
//        displayMap();
//        if(!isDefeated){
//            receiveLoseStatus();
//            //first time print lose information
//            if (isDefeated){
//                out.println("You lose.");
//            }
//        }else{
//            receiveLoseStatus();
//        }
//        receiveWinner();
//        if(isOver()){
//            if(color.equals(winner)){
//                out.println("Congratulations! You win!");
//            }else {
//                out.println(winner+" wins.");
//            }
//        }
//    }
//    /**
//     * Receive the string info from the server into serverBuffer
//     */
//    public void receive() throws IOException {
//        StringBuilder sb = new StringBuilder();
//        sb.append(reader.readLine());
//        String receivedLine = reader.readLine();
//        if(receivedLine==null){
//            throw new IOException("");
//        }
//        while(!receivedLine.equals(END_OF_TURN)) {
//            sb.append("\n").append(receivedLine);
//            receivedLine = reader.readLine();
//        }
//        buffer = sb.toString();
//    }
//
//
//    /**
//     * Send one message to server
//     * @param message the message to send
//     */
//    public void send(String message) {
//        output.println(message);
//        output.println(END_OF_TURN);
//        output.flush(); // flush the output buffer
//    }
//
//
//
//
//    public void receiveCombatOutcome()throws  IOException{
//        receive();
//        combatOutcome = buffer;
//    }
//
//    public void receiveWinner()throws  IOException{
//        receive();
//        winner = buffer;
//    }
//
//    public void receiveLoseStatus()throws IOException{
//        receive();
//        if(buffer.equals("lose")){
//            isDefeated = true;
//        }
//    }
//
//
//
//
//
//
//
//    /**
//     * Determine if a string is a non-negative number string
//     * @param number the string to be judged
//     * @return true is >=0. Otherwise, false
//     */
//    public boolean isPositiveInt(String number){
//        return Integer.parseInt(number) > 0;
//    }
//
//    /**
//     * Player do her turn
//     * @throws IOException if something wrong with receive
//     */
//    public void doOneTurn()throws IOException{
//        String choice;
//        label:
//        while(true) {
//            receive();
//            while (true) {
//                try {
//                    choice = tryChooseOneAction(buffer, input);
//                } catch (IllegalArgumentException e) {
//                    out.println(e.getMessage());
//                    System.out.println("Please input an valid action choice");
//                    continue;
//                }
//                break;
//            }
////            System.out.println("===========" + choice + "===========");
//            switch (choice) {
//                case "M":
//                    if (doOneMove().equals("")) {
//                        System.out.println("Successfully moved\n");
//                        continue;
//                    }
//                    break;
//                case "A":
//                    if (doOneAttack().equals("")) {
//                        System.out.println("Successfully attacked\n");
//                        continue;
//                    }
//                    break;
//                case "D":
//                    break label;
//            }
//            System.out.println("Action failed\n");
//        }
//    }
//
//    /**
//     * player choose one action order to do
//     * @param prompt to prompt for action choices
//     * @param input the input buffer
//     * @return input choice string
//     * @throws IllegalArgumentException illegal input choice
//     * @throws IOException if something wrong with receive
//     */
//    public String tryChooseOneAction(String prompt,BufferedReader input)throws IllegalArgumentException,IOException{
//        out.print(prompt);
//        String s = input.readLine();
//        if(isValidChoice(s)){
////            System.out.println("xxxxxx"+s + "xxxxxx");
//            send(s);
//            return s;
//        }else{
//            throw new IllegalArgumentException("Action should be \"M\"(move) \"A\"(attack) or \"D\"(done)");
//        }
//    }
//
//    /**
//     * Determine if a string is a valid choice string
//     * @param s the string to be judged
//     * @return true valid. Otherwise, false
//     */
//    public boolean isValidChoice(String s){
//        return s.equals("M")||s.equals("A")||s.equals("D");
//    }
//
//    /**
//     * Player do move phase
//     * @throws IOException if something wrong with receive
//     */
//    public String doOneMove()throws IOException{
//        receive();
//        while (true) {
//            try {
//                trySendUnitNumber(buffer,input);
//            } catch (IllegalArgumentException e) {
//                out.println(e.getMessage());
//                continue;
//            }
//            break;
//        }
//        receive();
//        trySendSourceTerritory(buffer,input);
//        receive();
//        trySendDestinationTerritory(buffer,input);
//        receive();
//        if(!buffer.equals("")){
//            out.println(buffer);
//        }
//        return buffer;
//    }
//
//    /**
//     * try to send a valid unit number
//     * @param prompt the prompt for input
//     * @param input input buffer
//     * @throws IllegalArgumentException invalid unit number input
//     * @throws IOException if something wrong with receive
//     */
//    public void trySendUnitNumber(String prompt,BufferedReader input)throws IllegalArgumentException,IOException{
//        out.println(prompt);
//        String s = input.readLine();
//        if(isPositiveInt(s)){
//            send(s);
//        }else{
//            throw new IllegalArgumentException("Units number should be non_negative number");
//        }
//    }
//
//    /**
//     * try to send source territory
//     * @param prompt the prompt for input
//     * @param input input buffer
//     * @throws IOException if something wrong with receive
//     */
//    public void trySendSourceTerritory(String prompt,BufferedReader input)throws IOException{
//        out.println(prompt);
//        String s = input.readLine();
//        send(s);
//    }
//
//    /**
//     * try to send destination territory
//     * @param prompt the prompt for input
//     * @param input input buffer
//     * @throws IOException if something wrong with receive
//     */
//    public void trySendDestinationTerritory(String prompt,BufferedReader input)throws IOException{
//        trySendSourceTerritory(prompt,input);
//    }
//
//    /**
//     * user do attack phase
//     * @throws IOException if something wrong with receive
//     * @return null if not actually conduct
//     */
//    public String doOneAttack()throws IOException{
//        //to do
//        return doOneMove();
//    }
//
//
//
//
//    public void displayCombatOutcome(){
//        out.println(combatOutcome);
//    }
//
//    public boolean isOver(){
//        return !winner.equals("no winner");
//    }
//}
