package com.hms.hospital_service.controller;

import com.hms.hospital_service.dto.PageResponse;
import com.hms.hospital_service.entity.Hospital;
import com.hms.hospital_service.service.HospitalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospitals")
public class HospitalController {

    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    // ✅ POST: Add Hospital
    @PostMapping
    public Hospital addHospital(@RequestBody Hospital hospital) {
        return hospitalService.saveHospital(hospital);
    }

    // ✅ GET: List all hospitals (NO pagination)
    @GetMapping
    public List<Hospital> getAllHospitals() {
        return hospitalService.getAllHospitals();
    }

    // ✅ GET: Pagination (CLEAN DTO)
    @GetMapping("/page")
    public PageResponse<Hospital> getHospitalsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return hospitalService.getHospitalsWithPagination(page, size);
    }

    // ✅ GET: Hospital by ID (NO CONFLICT)
    @GetMapping("/id/{id}")
    public Hospital getHospitalById(@PathVariable Long id) {
        return hospitalService.getHospitalById(id);
    }

    // ✅ PUT: Update Hospital
    @PutMapping("/{id}")
    public Hospital updateHospital(
            @PathVariable Long id,
            @RequestBody Hospital hospital) {
        return hospitalService.updateHospital(id, hospital);
    }

    // ✅ DELETE: Delete Hospital
    @DeleteMapping("/{id}")
    public String deleteHospital(@PathVariable Long id) {
        hospitalService.deleteHospital(id);
        return "Hospital deleted successfully";
    }

    @GetMapping("/search")
    public PageResponse<Hospital> searchHospital(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String city,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ){
        return hospitalService.searchHospitals(
                name, city,page,size,sortBy,direction
        );
    }
}
