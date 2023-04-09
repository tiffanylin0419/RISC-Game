package edu.duke.ece651.team8.client.controller;

import edu.duke.ece651.team8.client.ServerStream;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class ActionController extends GameController implements Initializable {
    private String winner="no winner";
    private boolean isDefeated=false;
    private boolean moveButtonPressed=false, attackButtonPressed=false, upgradeButtonPressed=false, researchButtonPressed=false;

    @FXML
    Button info, move, attack, done, upgrade, research;
    @FXML
    Label in1, in2, in3;
    @FXML
    TextField input1, input2, input3;


    public ActionController(Stage stage, ServerStream ss, String colorS, String messageS, String playerInfoS,String mapS, int playerNum) {
        super(stage,ss,colorS,messageS,playerInfoS, mapS,playerNum);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize();
        notSeeInput();
    }

    private void seeInput(boolean canSee){
        in1.setVisible(canSee);
        in2.setVisible(canSee);
        in3.setVisible(canSee);
        input1.setVisible(canSee);
        input2.setVisible(canSee);
        input3.setVisible(canSee);
        enter.setVisible(canSee);
    }
    private void notSeeInput(){
        seeInput(false);
        input1.clear();
        input2.clear();
        input3.clear();
    }
    private void canSeeInput(String s1, String s2, String s3){
        seeInput(true);
        in1.setText(s1);
        in2.setText(s2);
        in3.setText(s3);
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
        else if (researchButtonPressed){
            serverStream.send("R");
            actionResearch();
            researchButtonPressed=false;
        }
        notSeeInput();
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
        serverStream.receive();
    }

    private void actionResearch() throws IOException{
        //todo
    }
    private void actionUpdate() throws IOException{
        //todo
    }

    @FXML
    public void infoAction() {
        //todo
    }
    @FXML
    public void moveAction(){
        moveButtonPressed=true;
        canSeeInput("amount","source","destination");
    }

    @FXML
    public void attackAction(){
        attackButtonPressed=true;
        canSeeInput("amount","source","destination");
    }

    @FXML
    public void upgradeAction()  throws IOException{
        upgradeButtonPressed=true;
        actionUpdate();
        serverStream.send("U");
    }

    @FXML
    public void researchAction(){
        researchButtonPressed=true;
        canSeeInput("amount","prev_level","next_level");
    }
    @FXML
    public void doneAction()throws IOException{
        serverStream.send("D");
        reportResult();
        if(!isOver() && !isDefeated){
            serverStream.receive();
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
        setPlayer(serverStream.read());

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
