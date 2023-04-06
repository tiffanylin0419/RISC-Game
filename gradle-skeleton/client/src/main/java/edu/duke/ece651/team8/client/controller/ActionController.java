package edu.duke.ece651.team8.client.controller;

import edu.duke.ece651.team8.client.ServerStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ActionController implements Initializable {
    public String colorS;
    public String messageS;
    public String mapS;
    public ServerStream serverStream;
    private Stage stage;

    private String winner="no winner";
    private boolean isDefeated=false;
    private boolean moveButtonPressed=false;
    @FXML
    Label color;
    @FXML
    TextField input;
    @FXML
    Label message;
    @FXML
    Label errorMessage;
    @FXML
    Button enter;

    @FXML
    Button show;
    @FXML
    Button move;
    @FXML
    Button attack;
    @FXML
    Button done;

    public void setMap(String maps){
        System.out.println(maps);
    }

    private void setColor(String colors){
        Platform.runLater(() -> {
            color.setText("Player: "+colors);
        });
    }

    private void setMessage(String messages){
        Platform.runLater(() -> {
            message.setText("Message: " + messages);
        });
    }

    private void setErrorMessage(String errorMessages){
        Platform.runLater(() -> {
            errorMessage.setText("Error: " + errorMessages);
        });
    }

    public ActionController(Stage stage, ServerStream ss, String colors, String messages, String maps) {
        this.stage = stage;
        this.serverStream = ss;
        this.colorS=colors;
        this.messageS=messages;
        this.mapS=maps;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setColor(colorS);
        setMessage(messageS);
        setMap(mapS);
        input.setVisible(false);
        enter.setVisible(false);
    }


    @FXML
    public void enter() throws IOException {
        if(moveButtonPressed){
            String s =input.getText();
            if(isPositiveInt(s)){
                setErrorMessage(s);
            }
        }
        moveButtonPressed=false;
    }

    @FXML
    public void showAction() throws IOException {
        String s =input.getText();
    }

    @FXML
    public void moveAction() throws IOException {
        moveButtonPressed=true;
        input.setVisible(true);
        enter.setVisible(true);
    }

    @FXML
    public void attackAction() throws IOException {
        String s =input.getText();
    }

    @FXML
    public void doneAction() throws IOException {
        String s =input.getText();
    }

    /**
     * try to send a valid unit number
     * @param prompt the prompt for input
     * @throws IllegalArgumentException invalid unit number input
     * @throws IOException if something wrong with receive
     */
    public void trySendUnitNumber(String prompt)throws IllegalArgumentException,IOException{
        System.out.println(prompt);
        String s = input.getText();
        if(isPositiveInt(s)){
            serverStream.send(s);
        }else{
            throw new IllegalArgumentException("Units number should be non_negative number");
        }
    }
    /**
     * try to send source territory
     * @param prompt the prompt for input
     * @throws IOException if something wrong with receive
     */
    public void trySendSourceTerritory(String prompt)throws IOException{
        System.out.println(prompt);
        String s = input.getText();
        serverStream.send(s);
    }

    /**
     * try to send destination territory
     * @param prompt the prompt for input
     * @throws IOException if something wrong with receive
     */
    public void trySendDestinationTerritory(String prompt)throws IOException{
        trySendSourceTerritory(prompt);
    }

    /**
     * Determine if a string is a non-negative number string
     * @param number the string to be judged
     * @return true is >=0. Otherwise, false
     */
    public boolean isPositiveInt(String number){
        try{return Integer.parseInt(number) > 0;}
        catch(Exception e) {
            return false;
        }
    }

    /*private void reportResult() throws IOException {
        String combatOutcome=serverStream.read();
        System.out.println(combatOutcome);

        String mapInfo = serverStream.read();
        System.out.println(mapInfo);
        //map display

        receiveLoseStatus();
        if (isDefeated){
            System.out.println("You lose.");
        }
        receiveWinner();
        if(isOver()){
            if(color.equals(winner)){
                System.out.println("Congratulations! You win!");
            }else {
                System.out.println(winner+" wins.");
            }
        }
    }
    private boolean isOver(){
        return !winner.equals("no winner");
    }
    private void receiveWinner() throws IOException{winner=serverStream.read();}
    private void receiveLoseStatus() throws IOException{
        if(serverStream.read().equals("lose")){
            isDefeated = true;
        }
    }*/

}
