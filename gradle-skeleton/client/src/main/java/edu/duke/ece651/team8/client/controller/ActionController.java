package edu.duke.ece651.team8.client.controller;

import edu.duke.ece651.team8.client.ServerStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
            color.setText(colors);
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
        //FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/fxml/ActionPage.fxml"));
        /*try {
            String s =input.getText();
            input.clear();
            setErrorMessage("");
            if(Integer.parseInt(s) <= 0){
                throw new IllegalArgumentException("Units number should be non_negative number");
            }
            else{
                serverStream.send(s);
            }
            setErrorMessage(serverStream.read());
        } catch (Exception e) {
            setErrorMessage(e.getMessage());
        }

        if(serverStream.getBuffer().equals("valid\n")){
            setMessage(serverStream.read());
        }
        String map = serverStream.read();
        System.out.println("map: "+map);*/

    }
    public void getResult() throws IOException {
        String combatOutcome=serverStream.read();
        System.out.println(combatOutcome);

        String mapInfo = serverStream.read();
        System.out.println(mapInfo);
        //map display

        if(!isDefeated){
            receiveLoseStatus();
            if (isDefeated){
                System.out.println("You lose.");
            }
        }else{
            receiveLoseStatus();
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
    public boolean isOver(){
        return !winner.equals("no winner");
    }
    public void receiveWinner() throws IOException{winner=serverStream.read();}
    public void receiveLoseStatus() throws IOException{
        if(serverStream.read().equals("lose")){
            isDefeated = true;
        }
    }

}
