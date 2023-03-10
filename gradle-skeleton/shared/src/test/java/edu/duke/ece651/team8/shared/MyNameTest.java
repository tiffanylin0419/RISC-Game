package edu.duke.ece651.team8.shared;

import static org.junit.jupiter.api.Assertions.*;

import edu.duke.ece651.team8.shared.MyName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MyNameTest {
  @Test
  public void test_getName() {
    Assertions.assertEquals("team8", MyName.getName());
  }

}
