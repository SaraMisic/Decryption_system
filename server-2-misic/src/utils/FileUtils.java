package utils;

import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtils {

    public static String getFileExtension(String file) {
        String fe = "";
        int i = file.lastIndexOf('.');
        if (i > 0) {
            fe = file.substring(i + 1);
        }

        return fe;
    }

    public static List<String> getInputFileNames(String pathToFolder) throws CustomException {
        List<String> nameOfFiles = new ArrayList<>();
        File folder = new File(pathToFolder);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles == null){
            throw new CustomException("Unable to resolve files in input directory");
        }
        for (File file : listOfFiles)
            nameOfFiles.add(file.getName());

        return nameOfFiles;
    }

    public static String fileToString(String filePath) throws CustomException {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            Path path = Paths.get(filePath);
            List<String> list = Files.readAllLines(path);
            for (String item : list)
                contentBuilder.append(item);

        } catch (IOException e) {
            throw new CustomException("Reading from input file failed: " + filePath);
        }

        return contentBuilder.toString();
    }

    public static void zipFiles(List<String> listFiles, String pathToOutput) throws CustomException {
        try {
            String zipFileName = pathToOutput + "\\decrypted.zip";
            File zipFile = new File(zipFileName);
            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);
            for (String file : listFiles) {
                zipFile(pathToOutput + "\\" + file, zos);
                File fileToDelete = new File(pathToOutput + "\\" + file);
                fileToDelete.delete();
            }
            zos.close();

        } catch (IOException e) {
            throw new CustomException("Zipping files failed");
        }
    }

    private static void zipFile(String fileName, ZipOutputStream zos) throws IOException {
        final int BUFFER = 1024;
        BufferedInputStream bis = null;

        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        bis = new BufferedInputStream(fis, BUFFER);

        ZipEntry zipEntry = new ZipEntry(file.getName());
        zos.putNextEntry(zipEntry);
        byte data[] = new byte[BUFFER];
        int count;
        while ((count = bis.read(data, 0, BUFFER)) != -1) {
            zos.write(data, 0, count);
        }
        // close entry every time
        zos.closeEntry();
        bis.close();
    }

}
