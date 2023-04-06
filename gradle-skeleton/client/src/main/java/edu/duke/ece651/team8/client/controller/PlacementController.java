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
    public String colorS;
    public String messageS;
    public String mapS;
    public ServerStream serverStream;
    private Stage stage;

    private int placeNum;

    private boolean success=true;
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

    public void setMap(String maps){
        System.out.println(maps);
    }

    private void setErrorMessage(String errorMessages){
        Platform.runLater(() -> {
            errorMessage.setText("Error: " + errorMessages);
        });
    }

    public PlacementController(Stage stage, ServerStream ss, String colorS, String messageS, String mapS, int placeNum) {
        this.stage = stage;
        this.serverStream = ss;
        this.colorS=colorS;
        this.messageS=messageS;
        this.mapS=mapS;
        this.placeNum=placeNum;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setColor(colorS);
        setMessage(messageS);
        setMap(mapS);
    }

    @FXML
    public void enter() throws IOException {
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/fxml/ActionPage.fxml"));
        try {
            String s =input.getText();
            input.clear();
            setErrorMessage("");
            if(Integer.parseInt(s) <= 0){
                throw new IllegalArgumentException("Units number should be non_negative number");
            }
            else{
                serverStream.send(s);
            }
            setErrorMessage(serverStream.read());
        } catch (Exception e) {
            setErrorMessage(e.getMessage());
        }

        if(serverStream.getBuffer().equals("valid\n")){
            setMessage(serverStream.read());
            placeNum-=1;
        }
        else{
            System.out.println(serverStream.getBuffer());
        }
        //doOnePlacement();


        if(placeNum<0){
            mapS = serverStream.read();
            loaderStart.setControllerFactory(c-> new ActionController(stage,serverStream,colorS, "Please enter action"));
            Scene scene = new Scene(loaderStart.load());
            stage.setScene(scene);
            stage.show();
        }

    }

    public void doOnePlacement() throws IOException{

    }

    /**
     * Determine if a string is a non-negative number string
     * @param number the string to be judged
     * @return true is >=0. Otherwise, false
     */
    public boolean isPositiveInt(String number){
        return Integer.parseInt(number) > 0;
    }
}

