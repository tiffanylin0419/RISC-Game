package edu.duke.ece651.team8.client.controller;

import edu.duke.ece651.team8.client.ServerStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginSignupController implements Initializable {
    private Stage stage;

    public ServerStream serverStream;
    public String errorMessageS;

    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    Label errorMessage;

    public void setErrorMessage(){
        Platform.runLater(() -> {
            errorMessage.setText("Error: " + errorMessageS);
        });
    }

    public LoginSignupController(Stage stage, ServerStream ss, String errorMessageS) {
        this.stage = stage;
        this.serverStream = ss;
        this.errorMessageS=errorMessageS;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(!errorMessageS.equals("")){
            setErrorMessage();
        }

    }

    @FXML
    public void signup() throws IOException {
        serverStream.receive();
        serverStream.send("S");
        sendUsernamePassword();

    }

    @FXML
    public void login() throws IOException {
        serverStream.receive();
        serverStream.send("L");
        sendUsernamePassword();
    }

    public void sendUsernamePassword() throws IOException{
        serverStream.receive();
        serverStream.send(username.getText());
        serverStream.receive();
        serverStream.send(password.getText());
        if(!serverStream.read().equals("Successfully login!")){
            FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/fxml/LoginSignupPage.fxml"));
            loaderStart.setControllerFactory(c-> new LoginSignupController(stage,serverStream,serverStream.getBuffer()));
            Scene scene = new Scene(loaderStart.load());
            stage.setScene(scene);
            stage.show();
        }else{
            FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/fxml/OldNewGamePage.fxml"));
            loaderStart.setControllerFactory(c-> new OldNewGameController(stage,serverStream,""));
            Scene scene = new Scene(loaderStart.load());
            stage.setScene(scene);
            stage.show();
        }
    }
}
