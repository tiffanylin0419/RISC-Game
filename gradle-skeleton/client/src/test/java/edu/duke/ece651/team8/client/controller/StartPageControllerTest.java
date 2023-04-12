package edu.duke.ece651.team8.client.controller;

import edu.duke.ece651.team8.shared.AbstractMapFactory;
import edu.duke.ece651.team8.shared.Server;
import edu.duke.ece651.team8.shared.V2MapFactory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import static org.mockito.Mockito.*;
import edu.duke.ece651.team8.client.ServerStream;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class StartPageControllerTest extends ApplicationTest{
    private Stage stage;
    private ServerStream serverStream;
    private StartPageController startPageController;
    private Server server;

    @BeforeEach
    void setUp() throws Exception {
        AbstractMapFactory factory = new V2MapFactory();
        server = new Server(8080, factory);
        server.run();

        serverStream = new ServerStream("localhost",8080);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/StartPage.fxml"));
        loader.setControllerFactory(c -> new StartPageController(stage, serverStream));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @Disabled
    @Test
    void testStart(FxRobot robot) {
        robot.clickOn("#start");
        verifyThat(".title", hasText("Signup/Login"));
    }

    @AfterEach
    void tearDown() throws IOException {
        server.stop();
    }
}