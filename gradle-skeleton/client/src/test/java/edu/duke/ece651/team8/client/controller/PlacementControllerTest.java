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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

class PlacementControllerTest extends ApplicationTest {

    private Stage stage;
    private ServerStream serverStream;

    static Server server;
    private static Thread serverThread;
    BufferedReader ServerReader;

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
        PrintWriter out = mock(PrintWriter.class);
        ServerReader = mock(BufferedReader.class);
        InputStream instream = mock(InputStream.class);
        this.serverStream = new ServerStream(ServerReader, out, instream);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Placement.fxml"));
        loader.setControllerFactory(c -> new PlacementController(stage, serverStream,"green","message","{\"level\":\"1\",\"food\":\"1\",\"tech\":\"1\"}","{\"map\":{\"a1\":{\"color\":\"Green\",\"army\":\"\"},\"a2\":{\"color\":\"Red\",\"army\":\"\"},\"a3\":{\"color\":\"Yellow\",\"army\":\"\"},\"a4\":{\"color\":\"Blue\",\"army\":\"\"},\"a5\":{\"color\":\"green\",\"army\":\"\"},\"a6\":{\"color\":\"green\",\"army\":\"\"},\"b1\":{\"color\":\"green\",\"army\":\"\"},\"b2\":{\"color\":\"green\",\"army\":\"\"},\"b3\":{\"color\":\"green\",\"army\":\"\"},\"b4\":{\"color\":\"green\",\"army\":\"\"},\"b5\":{\"color\":\"green\",\"army\":\"\"},\"b6\":{\"color\":\"green\",\"army\":\"\"}}}",1,2));
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
    public void test_enter_good() throws IOException {
        FxRobot robot=new FxRobot();
        robot.clickOn("#input").write("3");
        when(ServerReader.readLine()).thenReturn("valid\n").thenReturn("END_OF_TURN").thenReturn("valid\n").thenReturn("END_OF_TURN").thenReturn("{\"level\":\"1\",\"food\":\"1\",\"tech\":\"1\"}").thenReturn("END_OF_TURN").thenReturn("{\"map\":{\"a1\":{\"color\":\"green\",\"army\":\"\"},\"a2\":{\"color\":\"green\",\"army\":\"\"},\"a3\":{\"color\":\"green\",\"army\":\"\"},\"a4\":{\"color\":\"green\",\"army\":\"\"},\"a5\":{\"color\":\"green\",\"army\":\"\"},\"a6\":{\"color\":\"green\",\"army\":\"\"},\"b1\":{\"color\":\"green\",\"army\":\"\"},\"b2\":{\"color\":\"green\",\"army\":\"\"},\"b3\":{\"color\":\"green\",\"army\":\"\"},\"b4\":{\"color\":\"green\",\"army\":\"\"},\"b5\":{\"color\":\"green\",\"army\":\"\"},\"b6\":{\"color\":\"green\",\"army\":\"\"}}}").thenReturn("END_OF_TURN").thenReturn("").thenReturn("END_OF_TURN");
        robot.clickOn("#enter");
        verifyThat("#color", hasText("Player: green"));
    }


    @Test
    public void test_enter_bad() throws IOException {
        FxRobot robot=new FxRobot();
        robot.clickOn("#input").write("-3");
        when(ServerReader.readLine()).thenReturn("invalid").thenReturn("END_OF_TURN").thenReturn("invalid").thenReturn("END_OF_TURN").thenReturn("invalid").thenReturn("END_OF_TURN").thenReturn("success").thenReturn("END_OF_TURN").thenReturn("{\"map\":{\"a1\":{\"color\":\"green\",\"army\":\"\"},\"a2\":{\"color\":\"green\",\"army\":\"\"},\"a3\":{\"color\":\"green\",\"army\":\"\"},\"a4\":{\"color\":\"green\",\"army\":\"\"},\"a5\":{\"color\":\"green\",\"army\":\"\"},\"a6\":{\"color\":\"green\",\"army\":\"\"},\"b1\":{\"color\":\"green\",\"army\":\"\"},\"b2\":{\"color\":\"green\",\"army\":\"\"},\"b3\":{\"color\":\"green\",\"army\":\"\"},\"b4\":{\"color\":\"green\",\"army\":\"\"},\"b5\":{\"color\":\"green\",\"army\":\"\"},\"b6\":{\"color\":\"green\",\"army\":\"\"}}}").thenReturn("END_OF_TURN").thenReturn("").thenReturn("END_OF_TURN");
        serverStream.read();
        robot.clickOn("#enter");
        verifyThat("#color", hasText("Player: green"));

        robot.clickOn("#input").write("300j");
        when(ServerReader.readLine()).thenReturn("invalid").thenReturn("END_OF_TURN").thenReturn("invalid").thenReturn("END_OF_TURN").thenReturn("invalid").thenReturn("END_OF_TURN").thenReturn("success").thenReturn("END_OF_TURN").thenReturn("{\"map\":{\"a1\":{\"color\":\"green\",\"army\":\"\"},\"a2\":{\"color\":\"green\",\"army\":\"\"},\"a3\":{\"color\":\"green\",\"army\":\"\"},\"a4\":{\"color\":\"green\",\"army\":\"\"},\"a5\":{\"color\":\"green\",\"army\":\"\"},\"a6\":{\"color\":\"green\",\"army\":\"\"},\"b1\":{\"color\":\"green\",\"army\":\"\"},\"b2\":{\"color\":\"green\",\"army\":\"\"},\"b3\":{\"color\":\"green\",\"army\":\"\"},\"b4\":{\"color\":\"green\",\"army\":\"\"},\"b5\":{\"color\":\"green\",\"army\":\"\"},\"b6\":{\"color\":\"green\",\"army\":\"\"}}}").thenReturn("END_OF_TURN").thenReturn("").thenReturn("END_OF_TURN");
        serverStream.read();
        robot.clickOn("#enter");
        verifyThat("#color", hasText("Player: green"));
    }




    @AfterAll
    static void tearDown() throws IOException, InterruptedException {
        serverThread.interrupt();
        server.stop();
    }


}