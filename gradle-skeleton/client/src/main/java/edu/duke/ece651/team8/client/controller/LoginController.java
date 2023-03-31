package edu.duke.ece651.team8.client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private Socket socket;
    private DataOutputStream outputStream;
    private DataInputStream inputStream;

    @FXML
    void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // TODO: Send username and password to server via socket
        // and check if login is successful

        // If login is successful, navigate to another screen
    }

    @FXML
    void handleRegister(ActionEvent event) {
        // Navigate to the register screen
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
        try {
            this.outputStream = new DataOutputStream(socket.getOutputStream());
            this.inputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

