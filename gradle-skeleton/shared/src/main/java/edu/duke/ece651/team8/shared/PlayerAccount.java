package edu.duke.ece651.team8.shared;

import java.io.*;
import java.net.Socket;

public class PlayerAccount {
    /** streams pass to client*/
    private PrintWriter output;
    /** Reader for clients message*/
    private BufferedReader reader;
    private final String username;
    private String password;
    public PlayerAccount(PrintWriter output, BufferedReader reader, String username, String password) {
        this.username = username;
        this.password = password;
        this.output = output;
        this.reader = reader;
    }
    public boolean match(String otherUsername, String otherPassword){
        return username.equals(otherUsername) && password.equals(otherPassword);
    }

    public String getUsername() {
        return username;
    }

    public void updateIO(PrintWriter out, BufferedReader r) {
        output = out;
        reader = r;
    }
    public void updatePassword(String password) {}
    public PrintWriter getOutput() {
        return output;
    }
    public BufferedReader getReader() {
        return reader;
    }
}
