package com.poi.test.upload.controller;

import com.google.gson.Gson;
import com.poi.test.dto.Employee;
import com.poi.test.excel.ExcelUpload;
import com.poi.test.upload.service.EmployeeService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@RestController
@RequestMapping(value="/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @RequestMapping(value="/list")
    public String getList(){

        List<Employee> list = service.getList();

        String jsonStr = new Gson().toJson(list);

        return jsonStr;
    }

    @RequestMapping(value = "/insertEmployee")
    public String insertEmployee() {

        int rslt = 0;

        String filePath = "D:\\Folder\\poi-test\\employee.xlsx";

        rslt = service.insertExcel(filePath);

        return Integer.toString(rslt);
    }


    @RequestMapping(value="/getListWithExcel")
    public ResponseEntity<Resource> downloadListExcel() throws Exception{

        File file = null;

        FileOutputStream fos = new FileOutputStream("D:\\Folder\\poi-test\\dummy\\employee.xlsx");

        XSSFWorkbook workbook = service.getExcel();
        workbook.write(fos);

        Path path = Paths.get("D:\\Folder\\poi-test\\dummy\\employee.xlsx");

        Resource resource = new UrlResource(path.toUri());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employee_list.xlsx");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }




}
