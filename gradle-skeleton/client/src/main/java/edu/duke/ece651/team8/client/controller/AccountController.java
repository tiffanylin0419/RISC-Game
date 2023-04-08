package edu.duke.ece651.team8.client.controller;

import edu.duke.ece651.team8.client.ServerStream;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AccountController {

    private Stage stage;

    public ServerStream serverStream;

    public AccountController(Stage stage, ServerStream ss) {
        this.stage = stage;
        this.serverStream = ss;
    }

    @FXML
    public void login() throws IOException {
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/fxml/LoginPage.fxml"));
        loaderStart.setControllerFactory(c->{
            return new LoginController(stage,serverStream);
        });
        Scene scene = new Scene(loaderStart.load());
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void signup() throws IOException {
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/fxml/SignupPage.fxml"));
        loaderStart.setControllerFactory(c->{
            return new SignupController(stage,serverStream);
        });
        Scene scene = new Scene(loaderStart.load());
        stage.setScene(scene);
        stage.show();
    }

}
