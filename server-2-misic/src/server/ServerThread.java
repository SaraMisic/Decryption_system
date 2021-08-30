package server;

import app.AppCore;
import utils.CustomException;
import utils.FileUtils;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

public class ServerThread implements Runnable {
    private Socket socket;
    private Server serverMain;
    private String pathToOutputdir;

    public ServerThread(Socket socket, Server serverMain,String pathToOutputdir) {
        this.socket = socket;
        serverMain = this.serverMain;
        this.pathToOutputdir = pathToOutputdir;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            out.println("Hello client! :)");
            String pathToInput = in.readLine();

            String pathToOutput = pathToOutputdir;

            try {
                List<String> inputFileNames = FileUtils.getInputFileNames(pathToInput);
                AppCore.getInstance().decryption(inputFileNames,pathToInput,pathToOutput);
            } catch (CustomException e) {
                System.err.println(e.getMessage());
                out.println("Action failed. Reason: " + e.getMessage());
                socket.close();
                return;
            }
            out.println("Action successfully finished!");
            socket.close();
        } catch (IOException e) {
            System.err.println("Communication with client failed");
        }

    }




}
