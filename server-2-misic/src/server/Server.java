package server;

import app.AppCore;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private String pathToInput;
    Socket clientSocket = null;

    public String getPathToInput() {
        return pathToInput;
    }

    public Server(String pathToOutputDir,int portNumber) throws Exception {
        try {
            ServerSocket ss = new ServerSocket(portNumber);
            AppCore.getInstance().logAction("Server started on port " + portNumber);

            while (true) {
                clientSocket = ss.accept();
                AppCore.getInstance().logAction("Connected to client " + ss.getInetAddress().getHostAddress());
                ServerThread serverThread = new ServerThread(clientSocket,this,pathToOutputDir);
                Thread thread = new Thread(serverThread);
                thread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
