package edu.duke.ece651.team8.client.controller;

import edu.duke.ece651.team8.client.ServerStream;
import edu.duke.ece651.team8.shared.Territory;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;


public class GameNumController{
    private Stage stage;

    public ServerStream serverStream;


    @FXML
    Label message;

    @FXML
    HBox buttons;

    public GameNumController(Stage stage, ServerStream ss, String message) {
        this.stage = stage;
        this.serverStream = ss;
        setMessage(message);
        addButton(message);
    }

    public void setMessage(String messages){
        Platform.runLater(() -> {
            message.setText(messages);
        });
    }
    public void addButton(String messages){
        Platform.runLater(() -> {
            int count = messages.split("\n", -1).length - 1;
            System.out.println(count);
            for (int i = 0; i < count; i++) {
                Button button = new Button("Game " + i);
                int tempI=i;
                button.setOnAction(event -> {
                    try {
                        handleButtonClick(tempI);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                buttons.getChildren().add(button);
            }
        });
    }
    private void handleButtonClick(int n) throws IOException{
        serverStream.send(""+n);
        serverStream.receive();
        System.out.println(serverStream.getBuffer());
        int status = Integer.parseInt(serverStream.getBuffer());

        System.out.println("color: "+serverStream.read());
        String colorS = serverStream.getBuffer();


        System.out.println("playerInfo: "+serverStream.read());
        String playerInfoS=serverStream.getBuffer();

        serverStream.receive();//outcome

        System.out.println("map: "+serverStream.read());
        String mapS = serverStream.getBuffer();
        String loseStatus=serverStream.read();
        String winner=serverStream.read();

        FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/fxml/ActionPage.fxml"));
        loaderStart.setControllerFactory(c-> {
            try {
                return new ActionController(stage,serverStream,colorS, "Please Select Action",playerInfoS, mapS,2,loseStatus,winner);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Scene scene = new Scene(loaderStart.load());
        stage.setScene(scene);
        stage.show();
    }
}
