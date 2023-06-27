package com.poi.test.dao;

import com.poi.test.dto.Employee;
import com.poi.test.mappers.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDAO {

    @Autowired
    EmployeeMapper mapper;

    public List<Employee> getList() {
        return mapper.getList();
    }

    public int insertEmployee(Employee employee) {
        return mapper.insertEmployee(employee);
    }

}
