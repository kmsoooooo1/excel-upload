package com.poi.test.mappers;

import com.poi.test.dto.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface EmployeeMapper {

    public List<Employee> getList();
    public int insertEmployee(Employee employee);
    public int insertEmployees(List<Employee> list);

}
