package com.hms.doctor_service.repository;

import com.hms.doctor_service.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    // 🔍 Find by email (unique)
    Optional<Doctor> findByEmail(String email);

    // 🔍 Search by doctor name (case-insensitive, partial)
    Page<Doctor> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // 🔍 Search by specialization
    Page<Doctor> findBySpecializationIgnoreCase(String specialization, Pageable pageable);
}
