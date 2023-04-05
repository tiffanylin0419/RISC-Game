package edu.duke.ece651.team8.shared;

import java.io.IOException;
import java.net.Socket;

public class ClientLoginHandler implements Runnable {
    private Socket clientSocket;
    private Server server;

    public ClientLoginHandler(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
    }

    @Override
    public void run() {
//        try {
//            // handle client login here
//            authenticate();
//            joinGame();
//        } catch (IOException e) {
//            // handle exception
//        } finally {
//            try {
//                clientSocket.close();
//            } catch (IOException e) {
//                // handle exception
//            }
//        }
    }
//    public void joinGame() {
//        int num = 2;
//        GameThread gt = server.checkOpenGame(num);
//        if(gt != null) {
//            gt.join(this);
//        } else {
//            GameThread game = new GameThread();
//            server.addGame(game);
//        }
//
//    }
}
