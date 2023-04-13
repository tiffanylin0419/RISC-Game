package edu.duke.ece651.team8.client.controller;

import edu.duke.ece651.team8.client.ServerStream;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionControllerTest {
    private Stage stage;
    private ServerStream serverStream;
    @Test
    public void testConstructor() {
        ActionController sc=new ActionController(stage, serverStream,"","","","",2);
    }
}