package decryption;

import com.msg.ttp.encryption.suite.Encryptor;
import utils.CustomException;
import utils.FileUtils;

import java.io.*;

public class TxtDecryptor implements IFileDecryptor{
    private static TxtDecryptor instance = null;

    public static TxtDecryptor getInstance()
    {
        if(instance == null)
            instance = new TxtDecryptor();

        return instance;
    }

    @Override
    public void decrypt(String inputFileName, String inputFilePath,String key,String pathToOutput) throws CustomException {
        Writer bufferedWriter = null;
        String fileContent = FileUtils.fileToString(inputFilePath);
        String decrypted = Encryptor.decrypt(key,fileContent);
        try{
            Writer fileWriter = new FileWriter(pathToOutput + "\\" + inputFileName);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(decrypted);
            bufferedWriter.close();
        }catch (FileNotFoundException e) {
            throw new CustomException("Output file not found");
        } catch (IOException e) {
            throw new CustomException("Writing into output file failed");
        }
    }






}
