package decryption;

import com.msg.ttp.encryption.suite.Encryptor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sun.reflect.generics.tree.Tree;
import utils.CustomException;

import java.io.*;
import java.util.*;

public class XlsxDecryptor implements IFileDecryptor {
    private static XlsxDecryptor instance = null;

    public static XlsxDecryptor getInstance() {
        if (instance == null)
            instance = new XlsxDecryptor();

        return instance;
    }

    @Override
    public void decrypt(String inputFileName, String inputFilePath, String key, String pathToOutput) throws CustomException {
        File myFileInput = new File(inputFilePath);
        List<String> rowList = new ArrayList<>();
        Map<String, List<String>> decryptedContent = new TreeMap<>();

        FileInputStream fis = null;
        XSSFWorkbook myWorkBookInput = null;
        try {
            fis = new FileInputStream(myFileInput);
            myWorkBookInput = new XSSFWorkbook(fis);
            fis.close();
        } catch (FileNotFoundException e) {
            throw new CustomException("Input file not found: " + inputFilePath);
        } catch (IOException e) {
            throw new CustomException("Reading from input file failed: " + inputFilePath);
        }

        XSSFWorkbook myWorkBookOutput = new XSSFWorkbook();
        XSSFSheet spreadsheet = myWorkBookOutput.createSheet("Decrypted data ");
        XSSFRow rowOutput;

        XSSFSheet mySheetInput = myWorkBookInput.getSheetAt(0);
        Iterator rowIteratorInput = mySheetInput.iterator();  // Get iterator to all the rows in current sheet
        // Traversing over each row of XLSX file
        while (rowIteratorInput.hasNext()) {
            Row rowInput = (Row) rowIteratorInput.next();
            Iterator cellIterator = rowInput.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = (Cell) cellIterator.next();
                rowList.add(Encryptor.decrypt(key, cell.getStringCellValue()));

            }

            decryptedContent.put("\"" + rowInput.getRowNum() + "\"", new ArrayList<>(rowList));
            rowList.clear();
        }

        Set<String> keyId = decryptedContent.keySet();
        int rowId = 0;

        for (String keyOut : keyId) {

            rowOutput = spreadsheet.createRow(rowId++);
            List<String> list = decryptedContent.get(keyOut);
            String[] array = list.toArray(new String[0]);
            int cellid = 0;

            for (Object obj : (Object[]) array) {
                Cell cell = rowOutput.createCell(cellid++);
                cell.setCellValue((String) obj);
            }
        }

        FileOutputStream output = null;
        try {
            output = new FileOutputStream(new File(pathToOutput + "\\" + inputFileName));
            myWorkBookOutput.write(output);
            output.close();
        } catch (FileNotFoundException e) {
            throw new CustomException("Output file not found");
        } catch (IOException e) {
            throw new CustomException("Writing into output file failed");
        }


    }
}
