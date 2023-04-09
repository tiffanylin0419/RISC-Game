package edu.duke.ece651.team8.client.controller;

import edu.duke.ece651.team8.client.ServerStream;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

import java.io.*;

public class PlacementController extends GameController implements Initializable {
    private int placeNum;
    @FXML
    TextField input;

    public PlacementController(Stage stage, ServerStream ss, String colorS, String messageS, String mapS, int placeNum, int playerNum) {
        super(stage,ss,colorS,messageS,mapS,playerNum);
        this.placeNum=placeNum;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize();
    }

    @FXML
    public void enter() throws IOException {
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/fxml/ActionPage.fxml"));
        doOnePlacement();
        if(placeNum<=0){
            mapS = serverStream.read();
            messageS=serverStream.read();
            loaderStart.setControllerFactory(c-> new ActionController(stage,serverStream,colorS, messageS, mapS,playerNum));
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

