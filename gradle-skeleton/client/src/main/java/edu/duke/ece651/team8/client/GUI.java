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

public class GUI {
    public GUI(){}

    public static void LoginScene(Stage stage){
        Label label = new Label("Please login or create a new account");
        Button button1 = new Button("Login/Create");
        TextField textField = new TextField(); // create a new text field

        VBox vbox = new VBox(label,textField,button1); // wrap the TextField in a VBox

        StackPane root = new StackPane();
        root.getChildren().addAll(vbox);

        Scene scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        button1.setOnAction(e -> GameScene(stage));
        stage.show();
    }
    public static void GameScene(Stage stage) {
        Label label = new Label("Welcome to Scene 2!");

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

        HBox hbox = new HBox();
        hbox.getChildren().addAll(button1, button2, button3, button4, button5, button6);
        hbox.setSpacing(10); // Set spacing between buttons
        hbox.setAlignment(Pos.TOP_CENTER); // Center align the HBox

        StackPane root = new StackPane();
        StackPane.setMargin(hbox, new Insets(20, 0, 0, 20));
        root.getChildren().addAll(label,hbox);
        Scene scene2 = new Scene(root, 640, 480);
        stage.setScene(scene2);
    }
}
