package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginMessageTest {
    @Test
    public void test_constructor(){
        LoginMessage lm=new LoginMessage();
        assertNull(lm.getMessage()[0]);
        assertNull(lm.getMessage()[1]);

    }

    @Test void Test(){
        LoginMessage lm = new LoginMessage();
        lm.setMessage("123","qwe");
        assertEquals(lm.getMessage()[0],"123");
        assertEquals(lm.getMessage()[1],"qwe");
    }

}