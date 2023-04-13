package edu.duke.ece651.team8.client.controller;

import edu.duke.ece651.team8.client.ServerStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultController implements Initializable {
    String messageS;
    @FXML
    Label message;

    public void setMessage(String messages){
        Platform.runLater(() -> {
            message.setText(messages);
        });
    }

    public ResultController(Stage stage, ServerStream ss, String messages) {
        this.messageS=messages;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setMessage(messageS);
    }
}
