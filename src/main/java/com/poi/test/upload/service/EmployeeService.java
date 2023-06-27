package com.poi.test.upload.service;

import com.poi.test.dto.Employee;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EmployeeService {

    List<Employee> getList();

    int insertExcel(String filePath);

    XSSFWorkbook getExcel();

}
