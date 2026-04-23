package com.hms.reporting_service.repository;


import com.hms.reporting_service.entity.DailyRevenue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyRevenueRepository extends JpaRepository<DailyRevenue, Long> {
    Optional<DailyRevenue> findByRevenueDate(LocalDate revenueDate);
}
