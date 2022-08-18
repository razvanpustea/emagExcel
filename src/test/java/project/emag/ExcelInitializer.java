package project.emag;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public final class ExcelInitializer {

    private Workbook workbook;

    public ExcelInitializer(String filePath, String fileName) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath + "/" + fileName);

        String fileExtension = fileName.substring(fileName.indexOf('.'));

        if (fileExtension.equals(".xlsx"))
            workbook = new XSSFWorkbook(fileInputStream);
        else if (fileExtension.equals(".xls"))
            workbook = new HSSFWorkbook(fileInputStream);
        else System.exit(-1);
    }

    public Workbook getWorkbook() {
        return workbook;
    }
}
