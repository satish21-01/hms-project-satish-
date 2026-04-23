package com.hms.doctor_service.controller;

import com.hms.doctor_service.dto.DoctorRequestDTO;
import com.hms.doctor_service.dto.DoctorResponseDTO;
import com.hms.doctor_service.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // ======================
    // CREATE DOCTOR
    // ======================
    @PostMapping
    public DoctorResponseDTO createDoctor(
            @Valid @RequestBody DoctorRequestDTO dto
    ) {
        return doctorService.createDoctor(dto);
    }

    // ======================
    // GET DOCTOR BY ID
    // ======================
    @GetMapping("/{id}")
    public DoctorResponseDTO getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }

    // ======================
    // GET ALL DOCTORS (PAGINATION + SORTING)
    // ======================
    @GetMapping
    public Page<DoctorResponseDTO> getAllDoctors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        return doctorService.getAllDoctors(page, size, sortBy, direction);
    }

    // ======================
    // SEARCH DOCTORS
    // ======================
    @GetMapping("/search")
    public Page<DoctorResponseDTO> searchDoctors(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String specialization,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return doctorService.searchDoctors(name, specialization, page, size);
    }

    // ======================
    // UPDATE DOCTOR (PUT)
    // ======================
    @PutMapping("/{id}")
    public DoctorResponseDTO updateDoctor(
            @PathVariable Long id,
            @Valid @RequestBody DoctorRequestDTO dto
    ) {
        return doctorService.updateDoctor(id, dto);
    }

    // ======================
    // DELETE DOCTOR
    // ======================
    @DeleteMapping("/{id}")
    public void deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
    }
    @GetMapping("/{id}/availability")
    public boolean checkAvailability(@PathVariable Long id) {
        return doctorService.isDoctorAvailable(id);
    }

}
