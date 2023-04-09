package edu.duke.ece651.team8.client.controller;

import edu.duke.ece651.team8.client.ServerStream;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class OldNewGameController {
    private Stage stage;

    public ServerStream serverStream;

    public OldNewGameController(Stage stage, ServerStream ss) {
        this.stage = stage;
        this.serverStream = ss;
    }

    @FXML
    public void oldGame() throws IOException {
        //to do
    }
    @FXML
    public void newGame() throws IOException {
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/fxml/PlayerNumPage.fxml"));
        loaderStart.setControllerFactory(c->{
            return new PlayerNumController(stage,serverStream);
        });
        Scene scene = new Scene(loaderStart.load());
        stage.setScene(scene);
        stage.show();
    }

}
