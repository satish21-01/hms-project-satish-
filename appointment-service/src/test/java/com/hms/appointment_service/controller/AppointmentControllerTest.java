package com.hms.appointment_service.controller;

import com.hms.appointment_service.security.JwtFilter;
import com.hms.appointment_service.security.JwtUtil;
import com.hms.appointment_service.service.AppointmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AppointmentController.class)
@AutoConfigureMockMvc(addFilters = false)
class AppointmentControllerTest {


    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private JwtFilter jwtFilter;

    @Autowired
    private MockMvc mockMvc;

    // 🔥 IMPORTANT FIX
    @MockBean
    private AppointmentService appointmentService;

    @Test
    void shouldReturn200() throws Exception {
        mockMvc.perform(get("/api/appointments"))
                .andExpect(status().isOk());
    }
}