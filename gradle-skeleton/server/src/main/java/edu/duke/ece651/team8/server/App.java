/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.team8.server;

import edu.duke.ece651.team8.shared.*;

import java.io.IOException;
import java.util.ArrayList;


public class App {
    /**
     * This main method runs the server, listening on port 1651.
     * Specifically, it creates an instance and calls run.
     * When done from the command line, this program runs until
     * externally killed??
     * @param args is the command line arguments.  These are currently ignored.
     * @throws IOException if creation of the ServerSocket fails.
    */
    public static void main(String[] args) throws IOException {
        AbstractMapFactory factory = new V1MapFactory();
        Map m = factory.createMap(4);
        ArrayList<Player> players=factory.createPlayers(4);
        Server server = new Server(1651, m, 4,players);
        server.run();
    }

}

