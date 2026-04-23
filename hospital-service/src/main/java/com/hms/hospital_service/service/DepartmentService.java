package com.hms.hospital_service.service;

import com.hms.hospital_service.entity.Department;
import com.hms.hospital_service.entity.Hospital;
import com.hms.hospital_service.repository.DepartmentRepository;
import com.hms.hospital_service.repository.HospitalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final HospitalRepository hospitalRepository;

    public DepartmentService(DepartmentRepository departmentRepository,
                             HospitalRepository hospitalRepository){

        this.departmentRepository =departmentRepository;
        this.hospitalRepository =hospitalRepository;

    }

    // add department under a hospital
    public Department addDepartment(Long hospitalId,Department department){
        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new RuntimeException("Hospital not found with id:"  + hospitalId));

        department.setHospital(hospital);
        return departmentRepository.save(department);
    }


     // get all department of a hospital
    public List<Department> getDepartmentsByHospital(Long hospitalId){
        return departmentRepository.findByHospitalId(hospitalId);
    }
}
