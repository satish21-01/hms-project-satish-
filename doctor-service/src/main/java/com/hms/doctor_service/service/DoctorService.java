package com.hms.doctor_service.service;

import com.hms.doctor_service.dto.DoctorRequestDTO;
import com.hms.doctor_service.dto.DoctorResponseDTO;
import com.hms.doctor_service.entity.Doctor;
import com.hms.doctor_service.exception.BadRequestException;
import com.hms.doctor_service.exception.ResourceNotFoundException;
import com.hms.doctor_service.repository.DoctorRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    // ======================
    // CREATE DOCTOR
    // ======================
    public DoctorResponseDTO createDoctor(DoctorRequestDTO dto) {

        // (Optional but recommended) duplicate email check
        doctorRepository.findByEmail(dto.getEmail())
                .ifPresent(d -> {
                    throw new BadRequestException("Doctor with this email already exists");
                });
        Doctor doctor = new Doctor();
        doctor.setName(dto.getName());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setPhone(dto.getPhone());
        doctor.setEmail(dto.getEmail());
        doctor.setExperience(dto.getExperience());

        doctor.setAvailable(dto.isAvailable());

        Doctor saved = doctorRepository.save(doctor);
        return mapToResponse(saved);
    }

    // ======================
    // GET DOCTOR BY ID
    // ======================
    public DoctorResponseDTO getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor not found with id: " + id)
                );
        return mapToResponse(doctor);
    }

    // ======================
    // GET ALL (PAGINATION + SORTING)
    // ======================
    public Page<DoctorResponseDTO> getAllDoctors(
            int page,
            int size,
            String sortBy,
            String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return doctorRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    // ======================
    // SEARCH (NAME / SPECIALIZATION)
    // ======================
    public Page<DoctorResponseDTO> searchDoctors(
            String name,
            String specialization,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        if (name != null && !name.isBlank()) {
            return doctorRepository
                    .findByNameContainingIgnoreCase(name, pageable)
                    .map(this::mapToResponse);
        }

        if (specialization != null && !specialization.isBlank()) {
            return doctorRepository
                    .findBySpecializationIgnoreCase(specialization, pageable)
                    .map(this::mapToResponse);
        }

        return doctorRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    // ======================
    // UPDATE DOCTOR (PUT)
    // ======================
    public DoctorResponseDTO updateDoctor(Long id, DoctorRequestDTO dto) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor not found with id: " + id)
                );

        doctor.setName(dto.getName());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setPhone(dto.getPhone());
        doctor.setEmail(dto.getEmail());
        doctor.setExperience(dto.getExperience());

        // 🔥 MOST IMPORTANT FIX
        doctor.setAvailable(dto.isAvailable());

        Doctor updated = doctorRepository.save(doctor);
        return mapToResponse(updated);
    }

    // ======================
    // DELETE DOCTOR
    // ======================
    public void deleteDoctor(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Doctor not found with id: " + id);
        }

        doctorRepository.deleteById(id);
    }

    // ======================
    // ENTITY → RESPONSE DTO MAPPER
    // ======================
    private DoctorResponseDTO mapToResponse(Doctor doctor) {
        DoctorResponseDTO dto = new DoctorResponseDTO();
        dto.setId(doctor.getId());
        dto.setName(doctor.getName());
        dto.setSpecialization(doctor.getSpecialization());
        dto.setPhone(doctor.getPhone());
        dto.setEmail(doctor.getEmail());
        dto.setExperience(doctor.getExperience());
        dto.setCreatedAt(doctor.getCreatedAt());

        dto.setAvailable(doctor.isAvailable());   // 🔥 ADD THIS
        return dto;
    }
    // 🔥 ADD THIS METHOD
    public boolean isDoctorAvailable(Long doctorId) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new RuntimeException("Doctor not found with id: " + doctorId)
                );

        return doctor.isAvailable(); // entity field
    }
}
