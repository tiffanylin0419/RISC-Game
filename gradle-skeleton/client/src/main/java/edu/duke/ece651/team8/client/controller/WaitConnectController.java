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
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/fxml/Placement.fxml"));
        try {
            String colors = serverStream.read();
            String map = serverStream.read();
            int placeNum=Integer.parseInt(serverStream.read());
            String message=serverStream.read();
            loaderStart.setControllerFactory(c-> new PlacementController(stage,serverStream, colors, message, map, placeNum));
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        Scene scene = new Scene(loaderStart.load());
        stage.setScene(scene);
        stage.show();
    }
}
