package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TechFactoryTest {
    @Test
    public void testProduce(){
        TechFactory tf=new TechFactory(3);
        TechResource r=new TechResource(5);
        tf.produce(r);
        assertEquals(8,r.getAmount());

    }
}