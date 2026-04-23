package com.hms.hospital_service.service;

import com.hms.hospital_service.entity.Doctor;
import com.hms.hospital_service.entity.DoctorSchedule;
import com.hms.hospital_service.entity.DayOfWeek;
import com.hms.hospital_service.exception.BadRequestException;
import com.hms.hospital_service.exception.ResourceNotFoundException;
import com.hms.hospital_service.repository.DoctorRepository;
import com.hms.hospital_service.repository.DoctorScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class DoctorScheduleService {

    private final DoctorScheduleRepository scheduleRepository;
    private final DoctorRepository doctorRepository;

    public DoctorScheduleService(DoctorScheduleRepository scheduleRepository,
                                 DoctorRepository doctorRepository) {
        this.scheduleRepository = scheduleRepository;
        this.doctorRepository = doctorRepository;
    }

    public DoctorSchedule addSchedule(
            Long doctorId,
            DayOfWeek day,
            LocalTime startTime,
            LocalTime endTime
    ) {

        // 1️⃣ Doctor exists?
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Doctor not found with id: " + doctorId
                        )
                );





        // 2️⃣ Time validation
        if (startTime.isAfter(endTime) || startTime.equals(endTime)) {
            throw new RuntimeException("Start time must be before end time");
        }

        // 3️⃣ Duplicate schedule check
        List<DoctorSchedule> existingSchedules =
                scheduleRepository.findByDoctor_IdAndDay(doctorId, day);

        if (!existingSchedules.isEmpty()) {
            throw new BadRequestException(
                    "Schedule already exists for doctor on " + day
            );
        }

        // 4️⃣ Create schedule
        DoctorSchedule schedule = new DoctorSchedule();
        schedule.setDoctor(doctor);
        schedule.setDay(day);
        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);
        schedule.setActive(true);

        // 5️⃣ Save
        return scheduleRepository.save(schedule);
    }
}
