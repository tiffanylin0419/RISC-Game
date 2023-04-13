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

}