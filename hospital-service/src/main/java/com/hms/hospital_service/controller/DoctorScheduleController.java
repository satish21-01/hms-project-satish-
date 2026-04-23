package com.hms.hospital_service.controller;

import com.hms.hospital_service.entity.DoctorSchedule;
import com.hms.hospital_service.service.DoctorScheduleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import com.hms.hospital_service.entity.DayOfWeek;
import java.time.LocalTime;
@RestController
@RequestMapping("/api/doctors")
    public class DoctorScheduleController {

    private final DoctorScheduleService scheduleService;

    public DoctorScheduleController(DoctorScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/{doctorId}/schedule")
    public DoctorSchedule addSchedule(
            @PathVariable Long doctorId,
            @RequestParam DayOfWeek day,

            @RequestParam
            @DateTimeFormat(pattern = "HH:mm")
            LocalTime startTime,

            @RequestParam
            @DateTimeFormat(pattern = "HH:mm")
            LocalTime endTime
    ) {
        return scheduleService.addSchedule(
                doctorId,
                day,
                startTime,
                endTime
        );
    }
}
