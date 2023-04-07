package edu.duke.ece651.team8.client.controller;

import edu.duke.ece651.team8.client.ServerStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ActionController implements Initializable {
    public String colorS;
    public String messageS;
    public String mapS;
    public ServerStream serverStream;
    private Stage stage;

    private String winner="no winner";
    private boolean isDefeated=false;
    private boolean moveButtonPressed=false;
    private boolean attackButtonPressed=false;
    @FXML
    Label color, message, errorMessage;
    @FXML
    Button enter;
    @FXML
    Button show, move, attack, done;
    @FXML
    Label in1, in2, in3;
    @FXML
    TextField input1, input2, input3;

    public void setMap(String maps){
        System.out.println(maps);
    }

    private void setColor(String colors){
        Platform.runLater(() -> {
            color.setText("Player: "+colors);
        });
    }

    private void setMessage(String messages){
        Platform.runLater(() -> {
            message.setText("Message: " + messages);
        });
    }

    private void setErrorMessage(String errorMessages){
        Platform.runLater(() -> {
            errorMessage.setText("Error: " + errorMessages);
        });
    }

    public ActionController(Stage stage, ServerStream ss, String colors, String messages, String maps) {
        this.stage = stage;
        this.serverStream = ss;
        this.colorS=colors;
        this.messageS=messages;
        this.mapS=maps;
    }
    private void seeInput(boolean canSee){
        in1.setVisible(canSee);
        in2.setVisible(canSee);
        in3.setVisible(canSee);
        input1.setVisible(canSee);
        input2.setVisible(canSee);
        input3.setVisible(canSee);
        enter.setVisible(canSee);
        if(!canSee){
            input1.clear();
            input2.clear();
            input3.clear();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setColor(colorS);
        setMessage(messageS);
        setMap(mapS);
        seeInput(false);
    }


    @FXML
    public void enter() throws IOException {
        if(moveButtonPressed){
            serverStream.send("M");
            actionMoveAttack();
            moveButtonPressed=false;
        }
        else if(attackButtonPressed){
            serverStream.send("A");
            actionMoveAttack();
            attackButtonPressed=false;
        }
        seeInput(false);
    }

    private void actionMoveAttack() throws IOException {
        String amount =input1.getText();
        String source =input2.getText();
        String destination =input3.getText();
        setErrorMessage("");
        trySendUnitNumber(amount);
        trySendTerritory(source);
        trySendTerritory(destination);
        String errorMessage=serverStream.read();
        if(errorMessage.equals("")){
            errorMessage="action succeed";
        }
        setErrorMessage(errorMessage);
        setMessage(serverStream.read());
    }

    @FXML
    public void showAction() throws IOException {
    }

    @FXML
    public void moveAction(){
        moveButtonPressed=true;
        seeInput(true);
    }

    @FXML
    public void attackAction(){
        attackButtonPressed=true;
        seeInput(true);
    }

    @FXML
    public void doneAction()throws IOException{
        serverStream.send("D");
        reportResult();

        //if isOver, go to result page
        //if isDefeated, lock all buttons
        //load page again
        if(!isOver() && !isDefeated){
            setMessage(serverStream.read());
        }
    }

    /**
     * try to send a valid unit number
     * @param num the num to send
     * @throws IllegalArgumentException invalid unit number input
     * @throws IOException if something wrong with receive
     */
    public void trySendUnitNumber(String num)throws IllegalArgumentException,IOException{
        serverStream.receive();
        serverStream.send(num);
    }
    /**
     * try to send source territory
     * @param s the string to send
     * @throws IOException if something wrong with receive
     */
    public void trySendTerritory(String s)throws IOException{
        serverStream.receive();
        serverStream.send(s);
    }


    private void reportResult() throws IOException {
        String combatOutcome=serverStream.read();
        System.out.println(combatOutcome);

        setMap(serverStream.read());

        receiveLoseStatus();
        if (isDefeated){
            setMessage("You lose.");
        }
        receiveWinner();
        if(isOver()){
            if(colorS.equals(winner)){
                FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/fxml/ResultPage.fxml"));
                loaderStart.setControllerFactory(c-> new ResultController(stage,serverStream, "Congratulations! You win!"));
                Scene scene = new Scene(loaderStart.load());
                stage.setScene(scene);
                stage.show();
            }else {
                FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/fxml/ResultPage.fxml"));
                loaderStart.setControllerFactory(c-> new ResultController(stage,serverStream, "You lose. Player "+winner+" wins."));
                Scene scene = new Scene(loaderStart.load());
                stage.setScene(scene);
                stage.show();
            }
        }
    }
    private boolean isOver(){
        return !winner.equals("no winner");
    }
    private void receiveWinner() throws IOException{winner=serverStream.read();}
    private void receiveLoseStatus() throws IOException{
        if(serverStream.read().equals("lose")){
            isDefeated = true;
        }
    }

}
