package com.hms.hospital_service.service;
import com.hms.hospital_service.dto.PageResponse;
import com.hms.hospital_service.entity.Hospital;
import com.hms.hospital_service.repository.HospitalRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    public HospitalService(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    public Hospital saveHospital(Hospital hospital) {
        return hospitalRepository.save(hospital);
    }


    public PageResponse<Hospital> getHospitalsWithPagination(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Hospital> hospitalPage = hospitalRepository.findAll(pageable);

        return new PageResponse<>(
                hospitalPage.getContent(),        // data
                hospitalPage.getNumber(),         // page
                hospitalPage.getSize(),           // size
                hospitalPage.getTotalElements(),  // totalElements
                hospitalPage.getTotalPages()      // totalPages
        );
    }

    public Hospital updateHospital(Long id, Hospital updatedHospital) {

        Hospital existHospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital not found with id:" + id));

        existHospital.setName(updatedHospital.getName());
        existHospital.setAddress(updatedHospital.getAddress());
        existHospital.setPhone(updatedHospital.getPhone());

        return hospitalRepository.save(existHospital);


    }

    public void deleteHospital(Long id) {
        if (!hospitalRepository.existsById(id)) {
            throw new RuntimeException("Hospital not found with id:" + id);
        }
        hospitalRepository.deleteById(id);
    }

    public Hospital getHospitalById(Long id) {
        return hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital not found"));

    }

    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    public PageResponse<Hospital> searchHospitals(
            String name,
            String city,
            int page,
            int size,
            String sortBy,
            String direction
    ) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Hospital> hospitalPage;

        if (name != null && !name.isBlank()) {
            hospitalPage = hospitalRepository
                    .findByNameContainingIgnoreCase(name, pageable);

        } else if (city != null && !city.isBlank()) {
            hospitalPage = hospitalRepository
                    .findByAddressContainingIgnoreCase(city, pageable);

        } else {
            hospitalPage = hospitalRepository.findAll(pageable);
        }

        return new PageResponse<>(
                hospitalPage.getContent(),
                hospitalPage.getNumber(),
                hospitalPage.getSize(),
                hospitalPage.getTotalElements(),
                hospitalPage.getTotalPages()
        );
    }

}
