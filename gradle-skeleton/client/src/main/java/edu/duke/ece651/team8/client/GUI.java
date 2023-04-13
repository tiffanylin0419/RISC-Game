
package edu.duke.ece651.team8.client;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.application.Platform;
/*
public class GUI {
    int width;
    int height;
    int margin=20;

    Stage stage;

    Label color = new Label("Player: ");
    Label message = new Label("");
    Label error = new Label("");
    int techLevel=1;
    HBox input=new HBox();

    boolean isPlacement=false;
    public GUI(int width, int height, Stage stage){
        this.width=width;
        this.height=height;
        this.stage=stage;
    }

    public void basicScene(){
        Label label = new Label("Please login or create a new account");
        StackPane root = new StackPane(label);
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();
    }
    public void LoginScene(){
        Label label = new Label("Please login or create a new account");

        Label label1 = new Label("Account");
        Label label2 = new Label("Password");
        TextField textField1 = new TextField(); // create a new text field
        TextField textField2 = new TextField(); // create a new text field

        Button button1 = new Button("Login/Create");

        HBox hbox1=new HBox(label1,textField1);
        hbox1.setSpacing(10);

        HBox hbox2=new HBox(label2,textField2);
        hbox2.setSpacing(10);
        VBox vbox = new VBox(label,hbox1,hbox2,button1); // wrap the TextField in a VBox
        vbox.setSpacing(10);

        StackPane root = new StackPane(vbox);
        StackPane.setMargin(vbox, new Insets(margin, margin, margin, margin));
        Scene scene = new Scene(root, width, height);

        stage.setScene(scene);
        button1.setOnAction(e -> {
            String input1 = textField1.getText();
            String input2 = textField2.getText();
            System.out.println(input1);
            System.out.println(input2);
            GameSelectScene();});
        stage.show();
    }
    public void GameSelectScene(){
        Label label = new Label("Select a previous game or create a new game");

        Button oldGame = new Button("previous game");
        Button newGame = new Button("new game");

        VBox vbox = new VBox(label,oldGame,newGame); // wrap the TextField in a VBox
        vbox.setSpacing(10);

        StackPane root = new StackPane(vbox);
        StackPane.setMargin(vbox, new Insets(margin, margin, margin, margin));
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        oldGame.setOnAction(e -> GameScene());
        newGame.setOnAction(e -> GameScene());
        //newGame.setOnAction(e -> WaitScene("Waiting for players to join"));
        stage.show();
    }
    public void WaitScene(String buffer){
        Label label = new Label(buffer);
        StackPane root = new StackPane(label);
        Scene scene = new Scene(root, width, height);
        stage.setScene(scene);
        stage.show();
    }
    public void GameScene() {

        Label label2 = new Label("Technology Level: "+techLevel);
        VBox userInfo = new VBox(color,label2); // wrap the TextField in a VBox
        userInfo.setSpacing(10); // Set spacing between buttons
        userInfo.setAlignment(Pos.TOP_LEFT); // Center align the HBox

        Label label3 = new Label("Message:");
        VBox messages = new VBox(label3,message,error); // wrap the TextField in a VBox
        messages.setSpacing(10); // Set spacing between buttons
        messages.setAlignment(Pos.BOTTOM_LEFT); // Center align the HBox



        input.getChildren().add(messages);

        input.setSpacing(10);
        input.setAlignment(Pos.BOTTOM_LEFT); // Center align the HBox

        Button button1 = new Button("Show");
        button1.setOnAction(e -> System.out.println("Show clicked!"));
        Button button2 = new Button("Move");
        button2.setOnAction(e -> System.out.println("Move clicked!"));
        Button button3 = new Button("Attack");
        button3.setOnAction(e -> System.out.println("Attack clicked!"));
        Button button4 = new Button("Research");
        button4.setOnAction(e -> System.out.println("Research clicked!"));
        Button button5 = new Button("Upgrade");
        button5.setOnAction(e -> System.out.println("Upgrade clicked!"));
        Button button6 = new Button("Done");
        button6.setOnAction(e -> System.out.println("Done clicked!"));
        HBox buttons = new HBox(button1, button2, button3, button4, button5, button6);
        buttons.setSpacing(10); // Set spacing between buttons
        buttons.setAlignment(Pos.TOP_RIGHT); // Center align the HBox


        ImageView imageView = new ImageView(new Image("https://i.imgur.com/BOMs9ff.jpg"));
        imageView.setFitWidth(width*0.8);
        imageView.setPreserveRatio(true);

        StackPane root = new StackPane( imageView,input,userInfo,buttons);
        Scene scene2 = new Scene(root, width,height);
        StackPane.setMargin(userInfo, new Insets(margin, margin, margin, margin));
        StackPane.setMargin(buttons, new Insets(margin, margin, margin, margin));
        StackPane.setMargin(input, new Insets(margin, margin, margin, margin));
        stage.setScene(scene2);
    }
}
*/
