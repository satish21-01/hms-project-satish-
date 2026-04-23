package com.hms.hospital_service.controller;

import com.hms.hospital_service.entity.Department;
import com.hms.hospital_service.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    // Add department to a hospital
    //http://localhost:8082/api/departments/hospital
    @PostMapping("/hospital/{hospitalId}")
    public Department addDepartment(
            @PathVariable Long hospitalId,
            @RequestBody Department department) {
       return departmentService.addDepartment(hospitalId, department);
    }
    // get department of a hospital
    //http://localhost:8082//api/hospitals
    @GetMapping("/hospital/{hospitalId}")
    public List<Department> getDepartment(@PathVariable Long hospitalId){
        return departmentService.getDepartmentsByHospital(hospitalId);

}
    }