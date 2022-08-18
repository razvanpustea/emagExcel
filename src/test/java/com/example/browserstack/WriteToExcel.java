package com.example.browserstack;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class WriteToExcel {

    public static void main(String[] args) {
        final String pathToExcel = "/home/razvanpustea/Desktop/write-data.xlsx";

        try (
                FileInputStream fs = new FileInputStream(pathToExcel);
                Workbook wb = new XSSFWorkbook(fs);
                FileOutputStream fos = new FileOutputStream(pathToExcel);
                ) {
            Sheet sheet1 = wb.getSheetAt(0);
            int lastRow = sheet1.getLastRowNum();

            for (int i = 0; i <= lastRow; i++) {
                Row row = sheet1.getRow(i);
                Cell cell = row.createCell(2);
                cell.setCellValue("WriteintoExcel3");
            }

            wb.write(fos);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
