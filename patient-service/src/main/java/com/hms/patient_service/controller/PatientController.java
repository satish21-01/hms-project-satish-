package com.hms.patient_service.controller;

import com.hms.patient_service.dto.PatientPatchRequestDTO;
import com.hms.patient_service.dto.PatientRequestDTO;
import com.hms.patient_service.dto.PatientResponseDTO;
import com.hms.patient_service.entity.Patient;
import com.hms.patient_service.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;   // ✅ CORRECT IMPORT
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    //http://localhost:8084/api/patients
    @PostMapping
    public PatientResponseDTO create(@Valid @RequestBody PatientRequestDTO dto) {
        return patientService.createPatient(dto);
    }

    @GetMapping("/{id}")
    public PatientResponseDTO getById(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }

    @GetMapping
    public List<PatientResponseDTO> getAll() {
        return patientService.getAllPatients();
    }

    @PatchMapping("/{id}")
    public PatientResponseDTO patchPatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientPatchRequestDTO dto
    ) {
        return patientService.patchPatient(id, dto);
    }
    @GetMapping("/page")
    public Page<PatientResponseDTO> getPatientsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        return patientService.getPatientsPage(page, size, sortBy, direction);
    }
    @GetMapping("/search")
    public Page<PatientResponseDTO> searchPatients(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String gender,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        return patientService.searchPatients(
                name, phone, gender, page, size, sortBy, direction
        );
    }

}

