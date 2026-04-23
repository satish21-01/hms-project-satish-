package com.hms.patient_service.repository;



import com.hms.patient_service.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    // 🔍 Search by name (contains, case-insensitive)
    Page<Patient> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // 🔍 Search by phone
    Page<Patient> findByPhoneContaining(String phone, Pageable pageable);

    // 🔍 Search by gender
    Page<Patient> findByGenderIgnoreCase(String gender, Pageable pageable);
}
