package decryption;

import utils.CustomException;

public interface IFileDecryptor {
    public void decrypt(String inputFileName, String inputFilePath, String key, String pathToOutput) throws CustomException;

}
