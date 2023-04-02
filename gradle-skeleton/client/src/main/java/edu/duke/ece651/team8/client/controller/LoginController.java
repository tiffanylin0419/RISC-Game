package edu.duke.ece651.team8.client.controller;

import edu.duke.ece651.team8.client.ServerStream;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.concurrent.LinkedBlockingQueue;

public class LoginController {
    public ServerStream serverStream;
    private Stage stage;
    public String usernameString;
    public String passwordString;

    @FXML
    TextField username;

    @FXML
    TextField password;

    @FXML
    Text errorMessage;

    public LoginController(Stage stage, ServerStream ss) {
        this.stage = stage;
        this.serverStream = ss;
        System.out.println("input name and password.\n click login");
    }


    @FXML
    public void tryLogin() throws IOException {
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/fxml/ChooseGamePage.fxml"));
//
//        loaderStart.setControllerFactory(c -> {
//            return new ChooseGameController(stage, serverStream);
//        });
//
//        Scene scene = new Scene(loaderStart.load());
//        stage.setScene(scene);
//        stage.show();
//
        this.usernameString = username.getText();
        this.passwordString = password.getText();

        if (!(usernameString.equals("") || passwordString.equals(""))) {
            serverStream.send(usernameString);
            serverStream.send(passwordString);
            String account_authentication = null;
            //account check
            try {
                serverStream.receive();
                account_authentication = serverStream.getBuffer();

            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }

            if (account_authentication.equals("true")) {
                loaderStart.setControllerFactory(c -> {
                    return new ChooseGameController(stage, serverStream);
                });

                Scene scene = new Scene(loaderStart.load());
                stage.setScene(scene);
                stage.show();
            } else {
                errorMessage.setText("your password is wrong!");
            }


        } else {
            username.setText("");
            password.setText("");
        }


    }
}