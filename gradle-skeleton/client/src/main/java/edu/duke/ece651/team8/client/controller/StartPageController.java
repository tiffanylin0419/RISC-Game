package edu.duke.ece651.team8.client.controller;

import edu.duke.ece651.team8.client.ServerStream;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;

public class StartPageController {
    private Stage stage;

    public ServerStream serverStream;

    public StartPageController(Stage stage, ServerStream ss) {
        this.stage = stage;
        this.serverStream = ss;
    }

    @FXML
    public void login() throws IOException {
        System.out.println("click on log in");
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/fxml/LoginSignupPage.fxml"));
        loaderStart.setControllerFactory(c-> new LoginSignupController(stage,serverStream));
        Scene scene = new Scene(loaderStart.load());
        stage.setScene(scene);
        stage.show();
    }
}