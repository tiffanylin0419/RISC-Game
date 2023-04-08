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

import org.json.*;
import org.json.JSONObject;

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
        JSONObject jsonObj = new JSONObject(maps);
        JSONObject map = jsonObj.getJSONObject("map");
        JSONObject a1 = map.getJSONObject("a1");
        String army = a1.getString("army");
        System.out.println("hi\n"+army);
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
        doOnePlacement();
        if(placeNum<=0){
            mapS = serverStream.read();
            messageS=serverStream.read();
            loaderStart.setControllerFactory(c-> new ActionController(stage,serverStream,colorS, messageS, mapS));
            Scene scene = new Scene(loaderStart.load());
            stage.setScene(scene);
            stage.show();
        }

    }

    public void doOnePlacement() throws IOException{
        try {
            tryInputUnitNumberToPlace();
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
    }

    /**
     * User input the unit number to place
     * @throws Exception if something wrong with receive
     */
    public void tryInputUnitNumberToPlace() throws IllegalArgumentException{
        String s =input.getText();
        input.clear();
        setErrorMessage("");
        if(!isPositiveInt(s)){
            throw new IllegalArgumentException("Units number should be non_negative number");
        }
        else{
            serverStream.send(s);
        }
    }
    /**
     * Determine if a string is a non-negative number string
     * @param number the string to be judged
     * @return true is >=0. Otherwise, false
     */
    public boolean isPositiveInt(String number){
        try{return Integer.parseInt(number) > 0;}
        catch(Exception e) {
            return false;
        }
    }
}

