package com.hms.patient_service.service;

import com.hms.patient_service.dto.PatientPatchRequestDTO;
import com.hms.patient_service.dto.PatientRequestDTO;
import com.hms.patient_service.dto.PatientResponseDTO;
import com.hms.patient_service.entity.Patient;
import com.hms.patient_service.exception.BadRequestException;
import com.hms.patient_service.exception.ResourceNotFoundException;
import com.hms.patient_service.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // ✅ CREATE
    public PatientResponseDTO createPatient(PatientRequestDTO dto) {

            Patient patient = new Patient();
        patient.setUserId(dto.getUserId());   // ✅ FIX

        patient.setName(dto.getName());
            patient.setAge(dto.getAge());
            patient.setGender(dto.getGender());
            patient.setPhone(dto.getPhone());
            patient.setAddress(dto.getAddress());

            Patient saved = patientRepository.save(patient);
            return mapToResponse(saved);
        }

    // ✅ GET BY ID
    public PatientResponseDTO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found with id: " + id)
                );
        return mapToResponse(patient);
    }

    // ✅ GET ALL
    public List<PatientResponseDTO> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // 🔁 ENTITY → DTO mapper
    private PatientResponseDTO mapToResponse(Patient patient) {
        PatientResponseDTO dto = new PatientResponseDTO();
        dto.setUserId(patient.getUserId());   // ✅ ADD THIS

        dto.setId(patient.getId());
        dto.setName(patient.getName());
        dto.setAge(patient.getAge());
        dto.setGender(patient.getGender());
        dto.setPhone(patient.getPhone());
        dto.setAddress(patient.getAddress());
        dto.setCreatedAt(patient.getCreatedAt());
        return dto;
    }

    public PatientResponseDTO patchPatient(Long id, PatientPatchRequestDTO dto) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found with id: " + id)
                );

        if (dto.getName() != null) {
            patient.setName(dto.getName());
        }

        if (dto.getAge() != null) {
            patient.setAge(dto.getAge());
        }

        if (dto.getPhone() != null) {
            patient.setPhone(dto.getPhone());
        }

        if (dto.getGender() != null) {
            patient.setGender(dto.getGender());
        }

        if (dto.getAddress() != null) {
            patient.setAddress(dto.getAddress());
        }

        Patient updated = patientRepository.save(patient);
        return mapToResponse(updated);
    }
    public Page<PatientResponseDTO> getPatientsPage(
            int page,
            int size,
            String sortBy,
            String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return patientRepository.findAll(pageable)
                .map(this::mapToResponse);
    }
    public Page<PatientResponseDTO> searchPatients(
            String name,
            String phone,
            String gender,
            int page,
            int size,
            String sortBy,
            String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        // 🔍 Priority-based search (simple & clean)
        if (name != null && !name.isBlank()) {
            return patientRepository
                    .findByNameContainingIgnoreCase(name, pageable)
                    .map(this::mapToResponse);
        }

        if (phone != null && !phone.isBlank()) {
            return patientRepository
                    .findByPhoneContaining(phone, pageable)
                    .map(this::mapToResponse);
        }

        if (gender != null && !gender.isBlank()) {
            return patientRepository
                    .findByGenderIgnoreCase(gender, pageable)
                    .map(this::mapToResponse);
        }

        // ❌ No search param → return all
        return patientRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

}





