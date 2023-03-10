package edu.duke.ece651.team8.client;

import edu.duke.ece651.team8.client.App;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {
    @Test
    void test_GetMessage() {
      App a = new App();
      assertEquals("Hello from the client for team8", a.getMessage());
    }
}
