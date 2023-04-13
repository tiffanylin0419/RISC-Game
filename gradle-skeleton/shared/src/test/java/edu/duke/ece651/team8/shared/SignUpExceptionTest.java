package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SignUpExceptionTest {
    @Test
    public void testConstructor(){
        SignUpException se=new SignUpException("hi");
        assertEquals("hi",se.getMessage());
    }
}