package edu.duke.ece651.team8.client.controller;

import edu.duke.ece651.team8.client.ServerStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;

public class ActionController {

    public ServerStream serverStream;
    private Stage stage;

    private String winner="no winner";
    private boolean isDefeated=false;
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

    public ActionController(Stage stage, ServerStream ss, String colors, String messages) {
        this.stage = stage;
        this.serverStream = ss;
        setColor(colors);
        setMessage(messages);
    }


    @FXML
    public void enter() throws IOException {

        //doAllTurns();

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
