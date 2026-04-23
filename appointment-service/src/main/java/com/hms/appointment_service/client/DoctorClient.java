package com.hms.appointment_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "doctor-service",
        url = "http://localhost:8086"   // jab tak Eureka nahi hai
)
public interface DoctorClient {

    @GetMapping("/api/doctors/{id}/availability")
    boolean isDoctorAvailable(@PathVariable("id") Long doctorId);
}
