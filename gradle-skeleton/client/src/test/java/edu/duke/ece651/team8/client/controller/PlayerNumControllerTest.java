package edu.duke.ece651.team8.client.controller;

import edu.duke.ece651.team8.client.ServerStream;
import edu.duke.ece651.team8.shared.AbstractMapFactory;
import edu.duke.ece651.team8.shared.Server;
import edu.duke.ece651.team8.shared.V2MapFactory;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

class PlayerNumControllerTest extends ApplicationTest {

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
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintWriter out = mock(PrintWriter.class);
        ServerReader = mock(BufferedReader.class);
        InputStream instream = mock(InputStream.class);

        this.serverStream = new ServerStream(ServerReader, out, instream);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PlayerNumPage.fxml"));
        loader.setControllerFactory(c -> new PlayerNumController(stage, serverStream));
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
    void testp2() throws IOException {
        FxRobot robot=new FxRobot();
        when(ServerReader.readLine()).thenReturn("").thenReturn("END_OF_TURN").thenReturn("").thenReturn("END_OF_TURN").thenReturn("green").thenReturn("END_OF_TURN").thenReturn("{\"level\":\"1\",\"food\":\"1\",\"tech\":\"1\"}").thenReturn("END_OF_TURN").thenReturn("{\"map\":{\"a1\":{\"color\":\"green\",\"army\":\"\"},\"a2\":{\"color\":\"green\",\"army\":\"\"},\"a3\":{\"color\":\"green\",\"army\":\"\"},\"a4\":{\"color\":\"green\",\"army\":\"\"},\"a5\":{\"color\":\"green\",\"army\":\"\"},\"a6\":{\"color\":\"green\",\"army\":\"\"},\"b1\":{\"color\":\"green\",\"army\":\"\"},\"b2\":{\"color\":\"green\",\"army\":\"\"},\"b3\":{\"color\":\"green\",\"army\":\"\"},\"b4\":{\"color\":\"green\",\"army\":\"\"},\"b5\":{\"color\":\"green\",\"army\":\"\"},\"b6\":{\"color\":\"green\",\"army\":\"\"}}}").thenReturn("END_OF_TURN").thenReturn("2").thenReturn("END_OF_TURN");
        robot.clickOn("#p2");
    }

    @Test
    void testp3() throws IOException {
        FxRobot robot=new FxRobot();
        when(ServerReader.readLine()).thenReturn("").thenReturn("END_OF_TURN").thenReturn("").thenReturn("END_OF_TURN").thenReturn("green").thenReturn("END_OF_TURN").thenReturn("{\"level\":\"1\",\"food\":\"1\",\"tech\":\"1\"}").thenReturn("END_OF_TURN").thenReturn("{\"map\":{\"a1\":{\"color\":\"green\",\"army\":\"\"},\"a2\":{\"color\":\"green\",\"army\":\"\"},\"a3\":{\"color\":\"green\",\"army\":\"\"},\"a4\":{\"color\":\"green\",\"army\":\"\"},\"a5\":{\"color\":\"green\",\"army\":\"\"},\"a6\":{\"color\":\"green\",\"army\":\"\"},\"b1\":{\"color\":\"green\",\"army\":\"\"},\"b2\":{\"color\":\"green\",\"army\":\"\"},\"b3\":{\"color\":\"green\",\"army\":\"\"},\"b4\":{\"color\":\"green\",\"army\":\"\"},\"b5\":{\"color\":\"green\",\"army\":\"\"},\"b6\":{\"color\":\"green\",\"army\":\"\"},\"c1\":{\"color\":\"green\",\"army\":\"\"},\"c2\":{\"color\":\"green\",\"army\":\"\"},\"c3\":{\"color\":\"green\",\"army\":\"\"},\"c4\":{\"color\":\"green\",\"army\":\"\"},\"c5\":{\"color\":\"green\",\"army\":\"\"},\"c6\":{\"color\":\"green\",\"army\":\"\"}}}").thenReturn("END_OF_TURN").thenReturn("2").thenReturn("END_OF_TURN");
        robot.clickOn("#p3");
    }

    @Test
    void testp4() throws IOException {
        FxRobot robot=new FxRobot();
        when(ServerReader.readLine()).thenReturn("").thenReturn("END_OF_TURN").thenReturn("").thenReturn("END_OF_TURN").thenReturn("green").thenReturn("END_OF_TURN").thenReturn("{\"level\":\"1\",\"food\":\"1\",\"tech\":\"1\"}").thenReturn("END_OF_TURN").thenReturn("{\"map\":{\"a1\":{\"color\":\"green\",\"army\":\"\"},\"a2\":{\"color\":\"green\",\"army\":\"\"},\"a3\":{\"color\":\"green\",\"army\":\"\"},\"a4\":{\"color\":\"green\",\"army\":\"\"},\"a5\":{\"color\":\"green\",\"army\":\"\"},\"a6\":{\"color\":\"green\",\"army\":\"\"},\"b1\":{\"color\":\"green\",\"army\":\"\"},\"b2\":{\"color\":\"green\",\"army\":\"\"},\"b3\":{\"color\":\"green\",\"army\":\"\"},\"b4\":{\"color\":\"green\",\"army\":\"\"},\"b5\":{\"color\":\"green\",\"army\":\"\"},\"b6\":{\"color\":\"green\",\"army\":\"\"},\"c1\":{\"color\":\"green\",\"army\":\"\"},\"c2\":{\"color\":\"green\",\"army\":\"\"},\"c3\":{\"color\":\"green\",\"army\":\"\"},\"c4\":{\"color\":\"green\",\"army\":\"\"},\"c5\":{\"color\":\"green\",\"army\":\"\"},\"c6\":{\"color\":\"green\",\"army\":\"\"},\"d1\":{\"color\":\"green\",\"army\":\"\"},\"d2\":{\"color\":\"green\",\"army\":\"\"},\"d3\":{\"color\":\"green\",\"army\":\"\"},\"d4\":{\"color\":\"green\",\"army\":\"\"},\"d5\":{\"color\":\"green\",\"army\":\"\"},\"d6\":{\"color\":\"green\",\"army\":\"\"}}}").thenReturn("END_OF_TURN").thenReturn("2").thenReturn("END_OF_TURN");
        robot.clickOn("#p4");
    }



    @AfterAll
    static void tearDown() throws IOException, InterruptedException {
        serverThread.interrupt();
        server.stop();
    }


}