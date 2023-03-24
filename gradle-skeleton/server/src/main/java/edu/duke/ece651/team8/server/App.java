/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.team8.server;

import edu.duke.ece651.team8.shared.*;

import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
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
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter player number: ");
        String line =reader.readLine();
        int num = Integer.parseInt(line);

        AbstractMapFactory factory = new V1MapFactory();
        Server server = new Server(8080, num, factory);
        server.run();
    }
}

