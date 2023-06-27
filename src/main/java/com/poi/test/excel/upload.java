package com.poi.test.excel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.Iterator;

public class upload {

    public void readFile() {

        String filePath = "D:\\Folder\\poi-test\\employee.xlsx";

        try{
            FileInputStream is = new FileInputStream(filePath);

            XSSFWorkbook workbook = new XSSFWorkbook(is);

            System.out.println("워크시트 읽기");

            XSSFSheet sheets = workbook.getSheetAt(0);

            Iterator<Row> rows = sheets.rowIterator();

            while(rows.hasNext()) {
                Row row = rows.next();

                for(int i = 1; i < 5; i++) {
//                    if(row.getCell(i) != null) System.out.println(row.getCell(i));
                    System.out.println(row.getCell(i));
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }



    }

}
