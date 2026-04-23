package com.hms.hospital_service.repository;

import com.hms.hospital_service.entity.Hospital;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    Page<Hospital> findByNameContainingIgnoreCase(
            String name, Pageable pageable
    );

    Page<Hospital> findByAddressContainingIgnoreCase(
            String address, Pageable pageable
    );
}

