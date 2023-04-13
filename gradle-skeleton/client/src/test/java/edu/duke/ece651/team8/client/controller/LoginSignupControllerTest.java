package edu.duke.ece651.team8.client.controller;

import edu.duke.ece651.team8.client.ServerStream;
import edu.duke.ece651.team8.shared.AbstractMapFactory;
import edu.duke.ece651.team8.shared.Server;
import edu.duke.ece651.team8.shared.V2MapFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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

    static Server server;
    private static Thread serverThread;

    @BeforeAll
    static void setUp() throws Exception {
        AbstractMapFactory factory = new V2MapFactory();
        server = new Server(8080,  factory);
        serverThread = new Thread(() -> {
            server.run();
        });
        serverThread.start();
    }


    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.serverStream = new ServerStream("localhost",8080);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginSignupPage.fxml"));
        loader.setControllerFactory(c -> new LoginSignupController(stage, serverStream,"errorMessage"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testStartPageLoads() throws IOException, InterruptedException {
        // check that the start page loads successfully
        assertNotNull(stage);
        assertNotNull(stage.getScene());
        assertTrue(stage.getScene().getRoot().isVisible());
        assertTrue(stage.getScene().getRoot().isManaged());

    }

    @Test
    void testSignup() {
        FxRobot robot=new FxRobot();
        verifyThat("#errorMessage", hasText("Error: errorMessage"));
        robot.clickOn("#signup");
        verifyThat("#message", hasText("Message: "));
    }

    @Test
    void testLogin() {
        FxRobot robot=new FxRobot();
        robot.clickOn("#login");
        verifyThat("#errorMessage", hasText("Error: Username or password not correct"));

    }

    @AfterAll
    static void tearDown() throws IOException, InterruptedException {
        serverThread.interrupt();
        server.stop();
    }


}