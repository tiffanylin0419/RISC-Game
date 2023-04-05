package edu.duke.ece651.team8.client.controller;

import edu.duke.ece651.team8.client.ServerStream;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WaitConnectController {
    private Stage stage;

    public ServerStream serverStream;


    public WaitConnectController(Stage stage, ServerStream ss) {
        this.stage = stage;
        this.serverStream = ss;
    }

    @FXML
    public void startPlacement() throws IOException {
        String colors =null;
        String message=null;
        String map=null;
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/fxml/Placement.fxml"));
        try {
            colors = serverStream.read();
            map = serverStream.read();
            System.out.println("color: "+colors);
            System.out.println("map: "+map);
            System.out.println("else: "+serverStream.read());
            message=serverStream.read();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        if(colors!=null && message!=null){
            String finalColors = colors;
            String finalMessage = message;
            loaderStart.setControllerFactory(c->{
                return new PlacementController(stage,serverStream, finalColors, finalMessage);
            });
        }

        Scene scene = new Scene(loaderStart.load());
        stage.setScene(scene);
        stage.show();
    }
}
