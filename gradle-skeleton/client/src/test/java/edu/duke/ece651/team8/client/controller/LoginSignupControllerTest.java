package edu.duke.ece651.team8.client.controller;

import edu.duke.ece651.team8.client.ServerStream;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

class LoginSignupControllerTest extends ApplicationTest {

    private Stage stage;
    private ServerStream serverStream;
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginSignupPage.fxml"));
        loader.setControllerFactory(c -> new LoginSignupController(stage, serverStream,"errorMessage"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testConstructor() throws IOException{
        FxRobot robot=new FxRobot();
        verifyThat("#errorMessage", hasText("Error: errorMessage"));
        //robot.clickOn("#signup");
        //verifyThat("#message", hasText("Message: "));
    }

}