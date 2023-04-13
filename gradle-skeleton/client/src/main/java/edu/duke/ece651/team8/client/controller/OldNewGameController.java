package edu.duke.ece651.team8.client.controller;

import edu.duke.ece651.team8.client.ServerStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.io.IOException;

public class OldNewGameController {
    private Stage stage;

    public ServerStream serverStream;

    @FXML
    Label message;

    public void setMessage(String messages){
        Platform.runLater(() -> {
            message.setText("Message: " + messages);
        });
    }

    public OldNewGameController(Stage stage, ServerStream ss, String s) {
        this.stage = stage;
        this.serverStream = ss;
        setMessage(s);
    }

    @FXML
    public void oldGame() throws IOException {
        serverStream.receive();
        serverStream.send("Y");
        try {
            int num = Integer.parseInt(serverStream.read());
            if(num == 0) System.out.println("No joined game in your list");
            newPage();
        } catch (Exception e) {
            System.out.println(serverStream.getBuffer());
            String s=serverStream.read();
            FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/fxml/GameNumPage.fxml"));
            loaderStart.setControllerFactory(c-> new GameNumController(stage,serverStream,s));
            Scene scene = new Scene(loaderStart.load());
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    public void newGame()  throws IOException{
        serverStream.receive();
        serverStream.send("N");
        newPage();
    }

    public void newPage()throws IOException{
        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/fxml/PlayerNumPage.fxml"));
        loaderStart.setControllerFactory(c->{
            return new PlayerNumController(stage,serverStream);
        });
        Scene scene = new Scene(loaderStart.load());
        stage.setScene(scene);
        stage.show();
    }

}
