package edu.duke.ece651.team8.client.controller;

import edu.duke.ece651.team8.client.ServerStream;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayerNumController {
    private Stage stage;

    public ServerStream serverStream;

    public PlayerNumController(Stage stage, ServerStream ss) {
        this.stage = stage;
        this.serverStream = ss;
    }

    @FXML
    public void p2() throws IOException {
        pAny("2",2);
    }
    @FXML
    public void p3() throws IOException {
        pAny("3",3);
    }
    @FXML
    public void p4() throws IOException {
        pAny("4",4);
    }

    private void pAny(String num, int n)throws IOException {
        serverStream.receive();
        serverStream.send("N");
        serverStream.receive();
        serverStream.send(num);
        serverStream.receive();

        String colors = serverStream.read();
        String playerInfo=serverStream.read();
        String map = serverStream.read();
        int placeNum=Integer.parseInt(serverStream.read());
        String message=serverStream.read();

        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/fxml/Placement.fxml"));
        loaderStart.setControllerFactory(c-> new PlacementController(stage,serverStream, colors, message, playerInfo,map, placeNum,n));
        Scene scene = new Scene(loaderStart.load());
        stage.setScene(scene);
        stage.show();
    }

}
