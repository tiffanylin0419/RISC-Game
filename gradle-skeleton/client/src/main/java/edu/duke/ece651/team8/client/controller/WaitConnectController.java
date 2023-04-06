package edu.duke.ece651.team8.client.controller;

import edu.duke.ece651.team8.client.ServerStream;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WaitConnectController {
    private String colorS="";
    private String mapS="";
    private Stage stage;

    public ServerStream serverStream;


    public WaitConnectController(Stage stage, ServerStream ss) {
        this.stage = stage;
        this.serverStream = ss;
    }

    @FXML
    public void startPlacement() throws IOException {
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/fxml/Placement.fxml"));
        loaderStart.setControllerFactory(c-> new PlacementController(stage,serverStream));
        Scene scene = new Scene(loaderStart.load());
        stage.setScene(scene);
        stage.show();
    }

}
