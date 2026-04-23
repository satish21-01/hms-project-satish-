package com.hms.appointment_service.repository;

import com.hms.appointment_service.entity.Appointment;
import com.hms.appointment_service.entity.AppointmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("""
SELECT a FROM Appointment a
WHERE a.doctorId = :doctorId
AND a.appointmentDate = :date
AND a.status = 'BOOKED'
AND (a.startTime < :endTime AND a.endTime > :startTime)
""")
    List<Appointment> findOverlappingAppointments(
            @Param("doctorId") Long doctorId,
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );

    // Doctor-wise appointments
    List<Appointment> findByDoctorId(Long doctorId);

    // Patient-wise appointments
    List<Appointment> findByPatientId(Long patientId);

    // Doctor + Date (optional but powerful)
    List<Appointment> findByDoctorIdAndAppointmentDate(Long doctorId, LocalDate appointmentDate);

    // Patient + Date (optional)
    List<Appointment> findByPatientIdAndAppointmentDate(Long patientId, LocalDate appointmentDate);

    Page<Appointment> findAll(Pageable pageable);

}