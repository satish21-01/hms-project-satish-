package com.hms.appointment_service.service;

import com.hms.appointment_service.client.DoctorClient;
import com.hms.appointment_service.dto.AppointmentDTO;
import com.hms.appointment_service.dto.AppointmentRequestDTO;
import com.hms.appointment_service.entity.Appointment;
import com.hms.appointment_service.entity.AppointmentStatus;
import com.hms.appointment_service.event.AppointmentBookedEvent;
import com.hms.appointment_service.exception.BadRequestException;
import com.hms.appointment_service.exception.SlotAlreadyBookedException;
import com.hms.appointment_service.kafka.AppointmentEventProducer;
import com.hms.appointment_service.repository.AppointmentRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorClient doctorClient;
    private final AppointmentEventProducer appointmentEventProducer;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            DoctorClient doctorClient,
            AppointmentEventProducer appointmentEventProducer
    ) {
        this.appointmentRepository = appointmentRepository;
        this.doctorClient = doctorClient;
        this.appointmentEventProducer = appointmentEventProducer;
    }

    private static final Logger log = LoggerFactory.getLogger(AppointmentService.class);

    @CircuitBreaker(name = "doctorService", fallbackMethod = "doctorFallback")
    public boolean checkDoctorAvailability(Long doctorId) {
        log.info("🔍 Checking doctor availability for doctorId: {}", doctorId);
        return doctorClient.isDoctorAvailable(doctorId);
    }

    public boolean doctorFallback(Long doctorId, Exception ex) {
        log.error("❌ Doctor service down, fallback triggered for doctorId: {}", doctorId);
        return true;
    }

    public Appointment bookAppointment(Appointment appointment) {

        log.info("👉 Booking appointment started for doctorId: {}", appointment.getDoctorId());

        // ✅ Validation
        if (appointment.getDoctorId() == null) {
            log.error("❌ DoctorId is null");
            throw new BadRequestException("DoctorId is required");
        }

        if (appointment.getPatientId() == null) {
            log.error("❌ PatientId is null");
            throw new BadRequestException("PatientId is required");
        }

        if (appointment.getAppointmentDate().isBefore(LocalDate.now())) {
            log.error("❌ Appointment date is in the past");
            throw new BadRequestException("Appointment date cannot be in the past");
        }

        if (appointment.getAppointmentDate().isEqual(LocalDate.now())
                && appointment.getStartTime().isBefore(java.time.LocalTime.now())) {
            log.error("❌ Appointment time is in the past");
            throw new BadRequestException("Appointment time cannot be in the past");
        }

        log.debug("✅ Validation passed for patientId: {}", appointment.getPatientId());

        // ✅ Slot check
        log.info("🔍 Checking slot availability for doctorId: {}", appointment.getDoctorId());

        List<Appointment> existingAppointments =
                appointmentRepository.findOverlappingAppointments(
                        appointment.getDoctorId(),
                        appointment.getAppointmentDate(),
                        appointment.getStartTime(),
                        appointment.getEndTime()
                );

        if (!existingAppointments.isEmpty()) {
            log.error("❌ Slot already booked for doctorId: {}", appointment.getDoctorId());
            throw new SlotAlreadyBookedException(
                    "Doctor already has an appointment in this time slot"
            );
        }

        // ✅ Save appointment
        appointment.setStatus(AppointmentStatus.BOOKED);
        Appointment savedAppointment = appointmentRepository.save(appointment);

        log.info("✅ Appointment booked successfully with id: {}", savedAppointment.getId());

        // ✅ Kafka event
        AppointmentBookedEvent event = new AppointmentBookedEvent();
        event.setAppointmentId(savedAppointment.getId());
        event.setDoctorId(savedAppointment.getDoctorId());
        event.setPatientId(savedAppointment.getPatientId());
        event.setAppointmentDate(savedAppointment.getAppointmentDate());
        event.setStartTime(savedAppointment.getStartTime());
        event.setEndTime(savedAppointment.getEndTime());
        event.setStatus(savedAppointment.getStatus());

        try {
            appointmentEventProducer.publishAppointmentBookedEvent(event);
            log.info("📩 Kafka event published for appointmentId: {}", savedAppointment.getId());
        } catch (Exception e) {
            log.error("❌ Kafka publish failed: {}", e.getMessage());
        }

        return savedAppointment;
    }

    public Appointment cancelAppointment(Long appointmentId) {
        log.info("❌ Cancelling appointment with id: {}", appointmentId);

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointment.setUpdatedAt(LocalDateTime.now());

        log.info("✅ Appointment cancelled successfully: {}", appointmentId);

        return appointmentRepository.save(appointment);
    }

    public Page<AppointmentDTO> getAppointments(
            int page, int size, String sortBy, String direction
    ) {
        log.info("📄 Fetching appointments with pagination");

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Appointment> appointments = appointmentRepository.findAll(pageable);

        return appointments.map(this::mapToDTO);
    }

    public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
        log.info("📋 Fetching appointments for doctorId: {}", doctorId);
        return appointmentRepository.findByDoctorId(doctorId);
    }

    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        log.info("📋 Fetching appointments for patientId: {}", patientId);
        return appointmentRepository.findByPatientId(patientId);
    }

    public List<Appointment> getAppointmentsByDoctorAndDate(
            Long doctorId, LocalDate date
    ) {
        log.info("📅 Fetching appointments for doctorId: {} on date: {}", doctorId, date);
        return appointmentRepository.findByDoctorIdAndAppointmentDate(doctorId, date);
    }

    public List<Appointment> getAppointmentsByPatientAndDate(
            Long patientId, LocalDate date
    ) {
        log.info("📅 Fetching appointments for patientId: {} on date: {}", patientId, date);
        return appointmentRepository.findByPatientIdAndAppointmentDate(patientId, date);
    }
    public AppointmentDTO bookAppointment(AppointmentRequestDTO request) {

        Appointment appointment = new Appointment();

        appointment.setDoctorId(request.getDoctorId());
        appointment.setPatientId(request.getPatientId());
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setStartTime(request.getStartTime());
        appointment.setEndTime(request.getEndTime());

        log.info("👉 Booking appointment started for doctorId: {}", request.getDoctorId());

        List<Appointment> existingAppointments =
                appointmentRepository.findOverlappingAppointments(
                        appointment.getDoctorId(),
                        appointment.getAppointmentDate(),
                        appointment.getStartTime(),
                        appointment.getEndTime()
                );

        if (!existingAppointments.isEmpty()) {
            log.error("❌ Slot already booked for doctorId: {}", appointment.getDoctorId());
            throw new SlotAlreadyBookedException(
                    "Doctor already has an appointment in this time slot"
            );
        }

        appointment.setStatus(AppointmentStatus.BOOKED);
        Appointment saved = appointmentRepository.save(appointment);

        log.info("✅ Appointment booked successfully with id: {}", saved.getId());

        return mapToDTO(saved);
    }
    private AppointmentDTO mapToDTO(Appointment appointment) {
        AppointmentDTO dto = new AppointmentDTO();

        dto.setId(appointment.getId());
        dto.setDoctorId(appointment.getDoctorId());
        dto.setPatientId(appointment.getPatientId());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setStartTime(appointment.getStartTime());
        dto.setEndTime(appointment.getEndTime());
        dto.setStatus(appointment.getStatus().name());

        return dto;
    }
    }

