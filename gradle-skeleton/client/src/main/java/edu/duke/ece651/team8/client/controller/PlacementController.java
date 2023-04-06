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
import java.net.URL;
import java.util.ResourceBundle;

import java.io.*;
import java.util.concurrent.LinkedBlockingQueue;

public class PlacementController implements Initializable {

    public ServerStream serverStream;
    private Stage stage;

    private int placementCount = 0;
    private int total=36;
    @FXML
    Label color;

    @FXML
    TextField input;

    @FXML
    Label message;
    @FXML
    Label errorMessage;

    @FXML
    Button enter;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        color.setText("no color");
        try {
            receiveColor();
            receiveMap();
            System.out.println("else: "+serverStream.read());
            String message=serverStream.read();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void setColor(String colors){
        Platform.runLater(() -> {
            color.setText("Player: " + colors);
        });
    }

    private void setMessage(String messages){
        Platform.runLater(() -> {
            message.setText("Message: " + messages);
        });
    }

    private void setErrorMessage(String errorMessages){
        Platform.runLater(() -> {
            errorMessage.setText("Error: " + errorMessages);
        });
    }

    public PlacementController(Stage stage, ServerStream ss) {
        this.stage = stage;
        this.serverStream = ss;
    }

    @FXML
    public void enter() throws IOException {


        /*FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/fxml/ActionPage.fxml"));
        try {
            String s =input.getText();
            input.clear();
            setErrorMessage("");
            if(Integer.parseInt(s) <= 0){
                throw new IllegalArgumentException("Units number should be non_negative number");
            }else if(Integer.parseInt(s) > total-5+placementCount){
                throw new IllegalArgumentException("Units number too big");
            }
            else{
                serverStream.send(s);
                total-=Integer.parseInt(s);
            }
            setErrorMessage(serverStream.read());
        } catch (Exception e) {
            setErrorMessage(e.getMessage());
        }

        if(serverStream.getBuffer().equals("valid\n")){
            placementCount+=1;
            setMessage(serverStream.read());
        }
        if(placementCount>=5){
            String map = serverStream.read();
            System.out.println("map: "+map);

            loaderStart.setControllerFactory(c->{
                return new ActionController(stage,serverStream,color.getText(), "Please enter action");
            });
            Scene scene = new Scene(loaderStart.load());
            stage.setScene(scene);
            stage.show();
        }*/
    }





    /**
     * receive color string from server
     * @throws IOException if something wrong with receive
     */
    public void receiveColor()throws  IOException{
        setColor(serverStream.read());
    }

    /**
     * receive map information from server
     * @throws IOException if something wrong with receive
     */
    public void receiveMap()throws  IOException{
        System.out.println(serverStream.read());
    }

}

