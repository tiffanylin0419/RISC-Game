package edu.duke.ece651.team8.client.controller;

import edu.duke.ece651.team8.client.ServerStream;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import javafx.fxml.Initializable;

import java.io.*;
import java.util.concurrent.LinkedBlockingQueue;

public class PlacementController {

    public ServerStream serverStream;
    private Stage stage;
    public String usernameString;
    public String passwordString;

    @FXML
    Label color;

    @FXML
    Label errorMessage;

    public void receiveColor()throws  IOException{
        color.setText("Player: "+serverStream.read());
    }


    public PlacementController(Stage stage, ServerStream ss) {
        this.stage = stage;
        this.serverStream = ss;
    }

    @FXML
    public void setPlayer(String playerName) {
        color.setText("Player: "+playerName);
    }
}

