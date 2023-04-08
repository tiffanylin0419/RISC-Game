package edu.duke.ece651.team8.client.controller;

import edu.duke.ece651.team8.client.ServerStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class GameController {
    public ServerStream serverStream;
    public Stage stage;
    public String colorS, messageS, mapS;

    @FXML
    Label color, message, errorMessage;
    @FXML
    Button enter;
    public Circle circles[];

    public GameController(Stage stage, ServerStream ss, String colorS, String messageS, String mapS){
        this.stage = stage;
        this.serverStream = ss;
        this.colorS=colorS;
        this.messageS=messageS;
        this.mapS=mapS;
    }
    public void initialize() {
        setColor(colorS);
        setMessage(messageS);
    }

    public void setColor(String colors){
        Platform.runLater(() -> {
            color.setText("Player: " + colors);
        });
    }
    public void setMessage(String messages){
        Platform.runLater(() -> {
            message.setText("Message: " + messages);
        });
    }
    public void setErrorMessage(String errorMessages){
        Platform.runLater(() -> {
            errorMessage.setText("Error: " + errorMessages);
        });
    }

}
