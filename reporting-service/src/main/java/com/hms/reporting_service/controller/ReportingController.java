package com.hms.reporting_service.controller;
import com.hms.reporting_service.entity.DailyRevenue;
import com.hms.reporting_service.repository.DailyRevenueRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportingController {

    private final DailyRevenueRepository repo;

    public ReportingController(DailyRevenueRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/daily")
    public List<DailyRevenue> getAllDaily() {
        return repo.findAll();
    }

    @GetMapping("/daily/{date}")
    public DailyRevenue getByDate(@PathVariable String date) {
        return repo.findByRevenueDate(LocalDate.parse(date))
                .orElseThrow(() -> new RuntimeException("No data"));
    }
}
