package com.hms.hospital_service.repository;

import com.hms.hospital_service.entity.DayOfWeek;
import com.hms.hospital_service.entity.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorScheduleRepository
                                             extends JpaRepository<DoctorSchedule, Long> {
    // All schedules of a doctor
    List<DoctorSchedule> findByDoctor_Id(Long doctorId);

    // Schedule of doctor for a specific day
    List<DoctorSchedule> findByDoctor_IdAndDay(
            Long doctorId,
            DayOfWeek day
    );

    // Only active schedules
    List<DoctorSchedule> findByDoctor_IdAndActiveTrue(Long doctorId);
}

