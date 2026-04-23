package com.hms.hospital_service.service;

import com.hms.hospital_service.entity.Department;
import com.hms.hospital_service.entity.Doctor;
import com.hms.hospital_service.entity.Hospital;
import com.hms.hospital_service.repository.DepartmentRepository;
import com.hms.hospital_service.repository.DoctorRepository;
import com.hms.hospital_service.repository.HospitalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;
    private final DepartmentRepository departmentRepository;

    public DoctorService(DoctorRepository doctorRepository,
                         HospitalRepository hospitalRepository,
                         DepartmentRepository departmentRepository){
        this.doctorRepository = doctorRepository;
        this.hospitalRepository= hospitalRepository;
        this.departmentRepository = departmentRepository;
    }
    // add doctor under hospital + department

    public Doctor addDoctor(Long hospitalId, Long departmentId, Doctor doctor){

        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new RuntimeException("Hospital not found with id:" + hospitalId));

        Department department = departmentRepository.findById(departmentId)
                 .orElseThrow(() -> new RuntimeException("Department not found with id:" + departmentId));

         doctor.setHospital(hospital);
         doctor.setDepartment(department);

         return doctorRepository.save(doctor);
    }

    // Get Doctors by hospital
    public List<Doctor> getDoctorByHospital(Long hospitalId){
        return doctorRepository.findByHospitalId(hospitalId);
    }

    //get doctors by department
    public List<Doctor> getDoctorsByDepartment(Long departmentId){
        return doctorRepository.findByDepartmentId(departmentId);
    }


}

