import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public Client(String serverAdress,int portNumber,String pathToInput) {
        try {
            Socket socket = new Socket(serverAdress,portNumber);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            System.out.println(in.readLine());
            out.println(pathToInput);

            String stateOfAction = in.readLine();
            System.out.println(stateOfAction);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

