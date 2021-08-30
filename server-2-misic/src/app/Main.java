package app;

import database.IDatabase;
import server.Server;
import database.SqliteDatabase;
import utils.FileUtils;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        if(args.length !=3)
        {
            System.out.println("Nisu uneti svi podaci u komandnu liniju!\n");
            return;
        }

        int portNumber = Integer.parseInt(args[0]);
        String pathToDatabase = args[1];
        String pathToOutputDir = args[2];

        IDatabase db = SqliteDatabase.getInstance();
        db.connect(pathToDatabase);

        AppCore appCore = AppCore.getInstance();
        appCore.setDb(db);

        Server server = new Server(pathToOutputDir,portNumber);
    }
}
