package app;

import database.IDatabase;
import database.SqliteDatabase;
import decryption.IFileDecryptor;
import decryption.TxtDecryptor;
import decryption.XlsxDecryptor;
import utils.CustomException;
import utils.FileUtils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AppCore {
    private static AppCore instance = null;
    private IDatabase db = null;


    public static AppCore getInstance() {
        if (instance == null)
            instance = new AppCore();

        return instance;
    }


    public void decryption(List<String> files, String pathToInput, String pathToOutput) throws CustomException {
        for (String file : files) {
            String key = db.getKey(file);
            logAction("Encryption key for file " + file + " read from the database");
            String inputFilePath = pathToInput + "\\" + file;
            String extension = FileUtils.getFileExtension(file);
            IFileDecryptor fileDecryptor = null;
            if (extension.equals("txt"))
            {
                fileDecryptor = TxtDecryptor.getInstance();

            } else if (extension.equals("xlsx")) {
                fileDecryptor = XlsxDecryptor.getInstance();
            }

            if (fileDecryptor == null) {
                throw new CustomException("File format not supported: " + extension);
            }

            fileDecryptor.decrypt(file, inputFilePath, key, pathToOutput);
            logAction("File " + file + " decrypted");

        }

        FileUtils.zipFiles(files, pathToOutput);
        logAction("Output files successfully zipped");

    }

    public void logAction(String action) {
        LocalDateTime dt = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");
        String formattedDate = dt.format(myFormatObj);

        System.out.println(formattedDate + " - " + action);

        db.logAction(action, formattedDate);
    }

    public void setDb(IDatabase db) {
        this.db = db;
    }

}
