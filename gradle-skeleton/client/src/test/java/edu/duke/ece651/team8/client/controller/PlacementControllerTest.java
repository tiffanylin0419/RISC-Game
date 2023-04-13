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

class PlacementControllerTest extends ApplicationTest {

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
    /*void testOld() {
        FxRobot robot=new FxRobot();
        robot.clickOn("#username").write("a");
        robot.clickOn("#signup");
        robot.clickOn("#old");
        verifyThat("#p2", hasText("2 Player"));
    }

    @Test
    void testNew() {
        FxRobot robot=new FxRobot();
        robot.clickOn("#signup");
        robot.clickOn("#new");
        verifyThat("#p2", hasText("2 Player"));
    }*/



    @AfterAll
    static void tearDown() throws IOException, InterruptedException {
        serverThread.interrupt();
        server.stop();
    }


}