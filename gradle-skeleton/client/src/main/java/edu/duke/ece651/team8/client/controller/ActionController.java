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
import javafx.application.Platform;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class ActionController extends GameController implements Initializable {
    private String winner="no winner";
    public boolean isDefeated=false;
    private boolean infoButtonPressed=false, moveButtonPressed=false, attackButtonPressed=false, upgradeButtonPressed=false, researchButtonPressed=false;

    @FXML
    Button info, move, attack, done, upgrade, research;
    @FXML
    Label in1, in2, in3, in4, territoryInfo;
    @FXML
    TextField input1, input2, input3, input4;



    public ActionController(Stage stage, ServerStream ss, String colorS, String messageS, String playerInfoS,String mapS, int playerNum) {
        super(stage,ss,colorS,messageS,playerInfoS, mapS,playerNum);
    }

    public ActionController(Stage stage, ServerStream ss, String colorS, String messageS, String playerInfoS,String mapS, int playerNum,String loseStatus, String winnerS) throws IOException {
        this(stage,ss,colorS,messageS,playerInfoS, mapS,playerNum);
        this.winner=winnerS;
        if(loseStatus.equals("lose")){
            setMessage("You lose.");
        }
        if(isOver()){
            if(colorS.equals(winner)){
                FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/fxml/OldNewGamePage.fxml"));
                loaderStart.setControllerFactory(c-> new OldNewGameController(stage,serverStream, "Congratulations! You win!"));
                Scene scene = new Scene(loaderStart.load());
                stage.setScene(scene);
                stage.show();
            }else {
                FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/fxml/OldNewGamePage.fxml"));
                loaderStart.setControllerFactory(c-> new OldNewGameController(stage,serverStream, "You lose. Player "+winner+" wins."));
                Scene scene = new Scene(loaderStart.load());
                stage.setScene(scene);
                stage.show();
            }
        }if(!isOver() && !isDefeated){
            serverStream.receive();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize();
        notSeeInput();
    }

    private void setTerritoryInfo(String s){
        territoryInfo.setText("Info: \n"+s);
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
        in4.setVisible(false);
        input4.setVisible(false);
        input1.clear();
        input2.clear();
        input3.clear();
        input4.clear();
    }
    private void canSeeInput(String s1, String s2, String s3){
        seeInput(true);
        in1.setText(s1);
        in2.setText(s2);
        in3.setText(s3);
    }
    private void upgradeInput(){
        canSeeInput("amount","prev_level","next_level");
        in4.setVisible(true);
        input4.setVisible(true);
    }

    private void see1input(String s1){
        in3.setVisible(true);
        input3.setVisible(true);
        enter.setVisible(true);
        in3.setText(s1);
    }
    @FXML
    public void enter() throws IOException {
        if(infoButtonPressed){
            String info=territoryArmys.get(input3.getText());
            if(info==null){
                setErrorMessage("Territory "+input3.getText()+" does not exist");
            }
            else{
                setTerritoryInfo(info);
                setErrorMessage("");
            }
            infoButtonPressed=false;
        }else if(moveButtonPressed){
            serverStream.send("M");
            actionMoveAttack();
            moveButtonPressed=false;
        }
        else if(attackButtonPressed){
            serverStream.send("A");
            actionMoveAttack();
            attackButtonPressed=false;
        }
        else if (upgradeButtonPressed){
            serverStream.send("U");
            actionUpgrade();
            upgradeButtonPressed=false;
        }
        notSeeInput();
    }

    public void setErrorAndPlayer() throws IOException{
        String errorMessage=serverStream.read();
        if(errorMessage.equals("")){
            errorMessage="action succeed";
        }
        setErrorMessage(errorMessage);
        setPlayer(serverStream.read());
        serverStream.receive();
    }

    private void actionMoveAttack() throws IOException {
        String amount =input1.getText();
        String source =input2.getText();
        String destination =input3.getText();
        setErrorMessage("");
        trySendUnitNumber(amount);
        trySendTerritory(source);
        trySendTerritory(destination);
        setErrorAndPlayer();
    }

    private void actionUpgrade() throws IOException{
        String territory =input4.getText();
        String amount =input1.getText();
        String prev_level =input2.getText();
        String next_level =input3.getText();
        trySendTerritory(territory);
        trySendUnitNumber(amount);
        trySendUnitNumber(prev_level);
        trySendUnitNumber(next_level);
        setErrorAndPlayer();
    }


    @FXML
    public void infoAction() {
        see1input("territory");
        infoButtonPressed=true;
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
    public void upgradeAction(){
        upgradeButtonPressed=true;
        upgradeInput();
    }

    @FXML
    public void researchAction() throws IOException {
        serverStream.send("R");
        if(!serverStream.read().equals("")){
            setErrorMessage(serverStream.getBuffer());
        }else{
            setErrorMessage("action succeed");
        }
        setPlayer(serverStream.read());
        serverStream.receive();

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
                FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/fxml/OldNewGamePage.fxml"));
                loaderStart.setControllerFactory(c-> new OldNewGameController(stage,serverStream, "Congratulations! You win!"));
                Scene scene = new Scene(loaderStart.load());
                stage.setScene(scene);
                stage.show();
            }else {
                FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/fxml/OldNewGamePage.fxml"));
                loaderStart.setControllerFactory(c-> new OldNewGameController(stage,serverStream, "You lose. Player "+winner+" wins."));
                Scene scene = new Scene(loaderStart.load());
                stage.setScene(scene);
                stage.show();
            }
        }

        /*else if(isDefeated){
            System.out.println("1\n"+serverStream.read());
            System.out.println("2\n"+serverStream.read());
            System.out.println("3\n"+serverStream.read());
            System.out.println("4\n"+serverStream.read());
            System.out.println("5\n"+serverStream.read());
            System.out.println("6\n"+serverStream.read());
            System.out.println("7\n"+serverStream.read());
            System.out.println("8\n"+serverStream.read());
            System.out.println("9\n"+serverStream.read());
            System.out.println("10\n"+serverStream.read());
            System.out.println("11\n"+serverStream.read());
            System.out.println("12\n"+serverStream.read());
            System.out.println("13\n"+serverStream.read());
            System.out.println("14\n"+serverStream.read());
            System.out.println("15\n"+serverStream.read());
            System.out.println("16\n"+serverStream.read());
            System.out.println("12\n"+serverStream.read());
        }*/
    }
    public boolean isOver(){
        return !winner.equals("no winner");
    }
    public void receiveWinner() throws IOException{winner=serverStream.read();}
    public void receiveLoseStatus() throws IOException{
        if(serverStream.read().equals("lose")){
            isDefeated = true;
        }
    }

}
