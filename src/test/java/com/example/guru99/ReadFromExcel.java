package com.example.guru99;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ReadFromExcel {

    public void readExcel(String filePath, String fileName, String sheetName) throws IOException {
        File file = new File(filePath + "/" + fileName);
        FileInputStream inputStream = new FileInputStream(file);
        Workbook workBook = null;
        String fileExtensionName = fileName.substring(fileName.indexOf("."));

        if (fileExtensionName.equals(".xlsx"))
            workBook = new XSSFWorkbook(inputStream);
        else if (fileExtensionName.equals(".xls"))
            workBook = new HSSFWorkbook(inputStream);

        Sheet sheet = workBook.getSheet(sheetName);
        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

        for (int i = 0; i < rowCount; i++) {
            Row row = sheet.getRow(i);

            for (int j = 0; j < row.getLastCellNum(); j++)
                System.out.print(row.getCell(j) + "|| ");

            System.out.println();
        }
    }

    public static void main(String... args) throws IOException {
        ReadFromExcel excelFile = new ReadFromExcel();
        final String filePath = "/home/razvanpustea/Desktop";

        excelFile.readExcel(filePath, "read-data.xlsx", "TestSheet");
    }
}
