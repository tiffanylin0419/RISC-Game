package edu.duke.ece651.team8.client.controller;

import edu.duke.ece651.team8.client.ServerStream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChooseGameController {
    private Stage stage;

    @FXML
    Button room1;
    @FXML
    Button room2;
    @FXML
    Button room3;

    public String real_color;
    public ServerStream serverStream;
    private List<Button> buttons;
    private HashMap<Button, String> buttonStatus;

    private void InitButtons(){
        buttons = new ArrayList<>();
        buttons.add(room1);
        buttons.add(room2);
        buttons.add(room3);
    }


    // initial game room according to the given map
    public void initialize() {

        InitButtons();
        try {
            for(Button button:buttons){
                String status = serverStream.read();
                buttonStatus.put(button,status);
            }
            for(Button button:buttons){
                if(buttonStatus.get(button).equals("start")){
                    button.setDisable(true);
                }
            }
        }
        catch(Exception e){
            e.getStackTrace();
        }
    }


    public ChooseGameController(Stage stage, ServerStream ss) {
        this.stage = stage;
        this.serverStream = ss;

    }

    @FXML
    public void JoinRoom1(ActionEvent actionEvent) throws Exception {
        joinGame(2, actionEvent);
    }

    @FXML
    public void JoinRoom2(ActionEvent actionEvent) throws Exception {
        joinGame(3, actionEvent);
    }

    @FXML
    public void JoinRoom3(ActionEvent actionEvent) throws Exception {
        joinGame(4, actionEvent);
    }

    private void joinGame(int playerNum, ActionEvent actionEvent) throws Exception {


    }

}