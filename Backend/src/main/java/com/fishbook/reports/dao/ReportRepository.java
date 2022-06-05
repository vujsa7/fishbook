package com.fishbook.reports.dao;

import com.fishbook.reports.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
