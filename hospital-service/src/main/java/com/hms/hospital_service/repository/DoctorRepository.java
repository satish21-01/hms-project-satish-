package com.hms.hospital_service.repository;

import com.hms.hospital_service.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository   extends JpaRepository<Doctor, Long> {

    List<Doctor> findByHospitalId(Long hospitalId);

    List<Doctor> findByDepartmentId(Long departmentId);
}
