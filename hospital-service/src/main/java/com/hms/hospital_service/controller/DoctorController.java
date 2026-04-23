package com.hms.hospital_service.controller;

import com.hms.hospital_service.entity.Doctor;
import com.hms.hospital_service.service.DoctorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService){
        this.doctorService = doctorService;
    }

    // Add Doctor
    //http://localhost:8082/api/doctors/hospital/2/department/1
    @PostMapping("/hospital/{hospitalId}/department/{departmentId}")
    public Doctor addDoctor(
            @PathVariable Long hospitalId,
            @PathVariable Long departmentId,
            @RequestBody Doctor doctor){
        return doctorService.addDoctor(hospitalId, departmentId, doctor);
    }

    // get doctors by hospital

    @GetMapping("/hospital/{hospitalId}")

    public List<Doctor> getDoctorsByHospital(@PathVariable Long hospitalId){
        return doctorService.getDoctorByHospital(hospitalId);
    }
    // get doctors by department

    @GetMapping("/department/{departmentId}")
    public List<Doctor> getDoctorByDepartment(@PathVariable Long departmentId){
        return doctorService.getDoctorsByDepartment(departmentId);
    }

}
