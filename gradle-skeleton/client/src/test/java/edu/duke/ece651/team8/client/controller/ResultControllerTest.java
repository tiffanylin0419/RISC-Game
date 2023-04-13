package edu.duke.ece651.team8.client.controller;

import edu.duke.ece651.team8.client.ServerStream;
import edu.duke.ece651.team8.shared.Server;
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

class ResultControllerTest extends ApplicationTest {
    private Stage stage;
    private ServerStream serverStream;
    private StartPageController startPageController;
    static Server server;
    private static Thread serverThread;
    private ResultController rc;
    /*@Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ResultPage.fxml"));
        loader.setControllerFactory(c->new ResultController(stage, serverStream,"hi"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testContent() throws IOException {
        verifyThat("#message", hasText("hi"));
    }*/

}