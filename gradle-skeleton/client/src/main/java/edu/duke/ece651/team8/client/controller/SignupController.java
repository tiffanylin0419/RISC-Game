package edu.duke.ece651.team8.client.controller;

import edu.duke.ece651.team8.client.ServerStream;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.io.IOException;


public class SignupController {

    private Stage stage;

    public ServerStream serverStream;

    @FXML
    TextField username;
    @FXML
    PasswordField password;
    public SignupController(Stage stage, ServerStream ss) {
        this.stage = stage;
        this.serverStream = ss;
    }

    @FXML
    public void signup() throws IOException {
        serverStream.receive();
        serverStream.send("S");
        serverStream.receive();
        serverStream.send(username.getText());
        serverStream.receive();
        serverStream.send(password.getText());
        if(!serverStream.read().equals("Successfully login!")){
            //load this page again
        }

        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/fxml/OldNewGamePage.fxml"));
        loaderStart.setControllerFactory(c-> new OldNewGameController(stage,serverStream));
        Scene scene = new Scene(loaderStart.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void login() throws IOException {
        serverStream.receive();
        serverStream.send("L");
        serverStream.receive();
        serverStream.send(username.getText());
        serverStream.receive();
        serverStream.send(password.getText());
        if(!serverStream.read().equals("Successfully login!")){
            //load this page again
        }

        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/fxml/OldNewGamePage.fxml"));
        loaderStart.setControllerFactory(c-> new OldNewGameController(stage,serverStream));
        Scene scene = new Scene(loaderStart.load());
        stage.setScene(scene);
        stage.show();
    }
}
