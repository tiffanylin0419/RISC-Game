package edu.duke.ece651.team8.client.controller;

import edu.duke.ece651.team8.shared.AbstractMapFactory;
import edu.duke.ece651.team8.shared.Server;
import edu.duke.ece651.team8.shared.V1MapFactory;
import edu.duke.ece651.team8.shared.V2MapFactory;
import javafx.scene.Node;
import org.checkerframework.framework.qual.DefaultQualifierForUse;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import edu.duke.ece651.team8.client.ServerStream;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class StartPageControllerTest extends ApplicationTest{
    private Stage stage;
    private ServerStream serverStream;
    private StartPageController startPageController;
    static Server server;
    private static Thread serverThread;
//    @BeforeAll
//    static void setUp() throws Exception {
//        AbstractMapFactory factory = new V2MapFactory();
//        server = new Server(8080,  factory);
//        serverThread = new Thread(() -> {
//            server.run();
//        });
//        serverThread.start();
//    }
//
//
//    @Override
//    public void start(Stage stage) throws Exception {
//        this.stage = stage;
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/StartPage.fxml"));
//        loader.setControllerFactory(c -> new StartPageController(stage, serverStream));
//        Scene scene = new Scene(loader.load());
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    @Test
//    public void testStartPageLoads() throws IOException, InterruptedException {
//        // check that the start page loads successfully
//        assertNotNull(stage);
//        assertNotNull(stage.getScene());
//        assertTrue(stage.getScene().getRoot().isVisible());
//        assertTrue(stage.getScene().getRoot().isManaged());
//
//    }
//
//    @Disabled
//    @Test
//    void testStart() {
//        FxRobot robot=new FxRobot();
//        verifyThat("#title", hasText("RISC game"));
//        robot.clickOn("#start");
//        verifyThat("#title", hasText("Signup/Login"));
//
//    }
//
//    @AfterAll
//    static void tearDown() throws IOException, InterruptedException {
//        serverThread.interrupt();
//        server.stop();
//        serverThread.join();
//    }

    /*@Test
    public void testConstructor() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/StartPage.fxml"));
        loader.setControllerFactory(c -> new StartPageController(null, null));
        Scene scene = new Scene(loader.load());
    }*/

}