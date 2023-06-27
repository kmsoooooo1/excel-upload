package com.poi.test.upload.service.impl;

import com.google.gson.Gson;
import com.poi.test.dao.EmployeeDAO;
import com.poi.test.dto.Employee;
import com.poi.test.mappers.EmployeeMapper;
import com.poi.test.upload.service.EmployeeService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeDAO dao;

    @Override
    public List<Employee> getList() {
        return dao.getList();
    }

    @Override
    @Transactional
    public int insertExcel(String filePath) {

        int rslt = 0;
        String jsonStr = "";
        XSSFWorkbook workbook = null;

        try{

            FileInputStream is = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(is);

            XSSFSheet sheets = workbook.getSheetAt(0);
            Iterator<Row> rows = sheets.rowIterator();

            List<Employee> list = new ArrayList<Employee>();
            Employee emp = null;

            while(rows.hasNext()) {
                Row row = rows.next();

                emp = new Employee();

                emp.setName((row.getCell(1)!=null?row.getCell(1).getStringCellValue():""));
                emp.setRank((row.getCell(2)!=null?row.getCell(2).getStringCellValue():""));
                emp.setAge((row.getCell(3)!=null)?Integer.parseInt(row.getCell(3).getStringCellValue()):0);
                emp.setCareer((row.getCell(4)!=null)?Integer.parseInt(row.getCell(4).getStringCellValue()):0);

                list.add(emp);
            }

            if(list.size()>0) {
                jsonStr = new Gson().toJson(list);
                System.out.println(jsonStr);

                for(Employee item : list) {
                    rslt = dao.insertEmployee(item);
                    if(rslt < 1) throw new Exception();
                }

            } else {
                rslt = -1;
            }


        }catch (Exception e){
            rslt = -1;
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }finally {
            if(workbook!=null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return rslt;
    }

    @Override
    public XSSFWorkbook getExcel() {

        List<Employee> list = dao.getList();

        XSSFWorkbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet();

        if(list != null && list.size() > 0) {

            for (int i = 0; i < list.size(); i++) {

                Employee emp = list.get(i);

                Row row = sheet.createRow(i+1);

//            1행 : employee_id
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(emp.getEmployee_id());
//            2행 : name
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(emp.getName());
//            3행 : rank
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(emp.getRank());
//            4행 : age
                Cell cell4 = row.createCell(3);
                cell4.setCellValue(emp.getAge());
//            5행 : career
                Cell cell5 = row.createCell(4);
                cell5.setCellValue(emp.getCareer());

            }
        }

        return workbook;
    }
}
