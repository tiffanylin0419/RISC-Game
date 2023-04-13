package edu.duke.ece651.team8.client.controller;

import edu.duke.ece651.team8.client.ServerStream;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlacementControllerTest {

    private Stage stage;
    private ServerStream serverStream;
    @Test
    public void testConstructor() {
        PlacementController sc=new PlacementController(stage, serverStream,"","","","",2,3);
    }

}