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
        //when(ServerReader.readLine()).thenReturn("3").thenReturn("END_OF_TURN").thenReturn("prompt").thenReturn("END_OF_TURN").thenReturn("valid\n");
        //robot.clickOn("#p2");
    }



    @AfterAll
    static void tearDown() throws IOException, InterruptedException {
        serverThread.interrupt();
        server.stop();
    }


}