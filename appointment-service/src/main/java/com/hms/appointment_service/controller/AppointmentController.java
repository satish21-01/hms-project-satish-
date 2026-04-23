package com.hms.appointment_service.controller;

import com.hms.appointment_service.dto.AppointmentDTO;
import com.hms.appointment_service.dto.AppointmentRequestDTO;
import com.hms.appointment_service.entity.Appointment;
import com.hms.appointment_service.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // ✅ PATIENT only
    @PreAuthorize("hasRole('PATIENT')")
    @PostMapping
    public AppointmentDTO bookAppointment(
            @RequestBody @Valid AppointmentRequestDTO request,
            Authentication auth
    ) {
        System.out.println("Booking by: " + auth.getName());
        return appointmentService.bookAppointment(request);
    }

    // ✅ DOCTOR only
    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/doctor/{doctorId}")
    public List<Appointment> getByDoctor(@PathVariable Long doctorId) {
        return appointmentService.getAppointmentsByDoctor(doctorId);
    }

    // ✅ PATIENT only
    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/patient/{patientId}")
    public List<Appointment> getByPatient(@PathVariable Long patientId) {
        return appointmentService.getAppointmentsByPatient(patientId);
    }

    // ✅ DOCTOR only
    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/doctor/{doctorId}/date/{date}")
    public List<Appointment> getByDoctorAndDate(
            @PathVariable Long doctorId,
            @PathVariable LocalDate date
    ) {
        return appointmentService.getAppointmentsByDoctorAndDate(doctorId, date);
    }

    // ✅ PATIENT only
    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/patient/{patientId}/date/{date}")
    public List<Appointment> getByPatientAndDate(
            @PathVariable Long patientId,
            @PathVariable LocalDate date
    ) {
        return appointmentService.getAppointmentsByPatientAndDate(patientId, date);
    }

    // ✅ PATIENT only
    @PreAuthorize("hasRole('PATIENT')")
    @PatchMapping("/{id}/cancel")
    public Appointment cancelAppointment(@PathVariable Long id) {
        return appointmentService.cancelAppointment(id);
    }

    // ✅ ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public Page<AppointmentDTO> getAllAppointments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "appointmentDate") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return appointmentService.getAppointments(page, size, sortBy, direction);
    }
}