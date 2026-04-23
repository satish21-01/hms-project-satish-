package com.hms.appointment_service.service;

import com.hms.appointment_service.controller.AppointmentController;
import com.hms.appointment_service.dto.AppointmentRequestDTO;
import com.hms.appointment_service.entity.Appointment;
import com.hms.appointment_service.entity.AppointmentStatus;
import com.hms.appointment_service.repository.AppointmentRepository;

import com.hms.appointment_service.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = AppointmentController.class)
@AutoConfigureMockMvc(addFilters = false)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }
    // 🔥 ADD THIS
    @MockBean
    private JwtUtil jwtUtil;

    @Test
    void shouldBookAppointmentSuccessfully() {

        AppointmentRequestDTO request = new AppointmentRequestDTO();
        request.setDoctorId(1L);
        request.setPatientId(1L);
        request.setAppointmentDate(LocalDate.now());
        request.setStartTime(LocalTime.of(10, 0));
        request.setEndTime(LocalTime.of(10, 30));

        Appointment saved = new Appointment();
        saved.setId(1L);
        saved.setStatus(AppointmentStatus.BOOKED);

        when(appointmentRepository.save(any(Appointment.class)))
                .thenReturn(saved);

        var result = appointmentService.bookAppointment(request);

        assertNotNull(result);
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }
}