package edu.duke.ece651.team8.client.controller;

import edu.duke.ece651.team8.client.ServerStream;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.control.*;
import javafx.application.Platform;

import javafx.fxml.Initializable;

import java.io.*;
import java.util.concurrent.LinkedBlockingQueue;

public class PlacementController {

    public ServerStream serverStream;
    private Stage stage;

    private int placementCount = 0;
    private boolean haveMessage=true;
    @FXML
    Label color;

    @FXML
    TextField unit;

    @FXML
    Label message;
    @FXML
    Label errorMessage;

    @FXML
    Button enter;


    public PlacementController(Stage stage, ServerStream ss, String colors, String messages) {
        this.stage = stage;
        this.serverStream = ss;
        Platform.runLater(() -> {
            color.setText("Player: " + colors);
            message.setText("Message: " + messages);
        });
    }

    @FXML
    public void enter() throws IOException {

        //FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/fxml/ChooseGamePage.fxml"));

        try {
            String s =unit.getText();
            unit.clear();
            if(Integer.parseInt(s) > 0){
                serverStream.send(s);
            }else{
                throw new IllegalArgumentException(placementCount+"Units number should be non_negative number");
            }
            haveMessage=true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(placementCount+"Please input a valid placement!");
            haveMessage=false;
        }
        if(haveMessage) {
            System.out.println(serverStream.read());
        }
        if(serverStream.getBuffer().equals("valid\n")){
            placementCount+=1;
            haveMessage=true;
        }else{
            haveMessage=false;
        }
        if(haveMessage) {
            System.out.print(serverStream.read());
        }
    }
}

