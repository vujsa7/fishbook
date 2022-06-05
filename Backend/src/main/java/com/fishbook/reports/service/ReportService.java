package com.fishbook.reports.service;

import com.fishbook.reports.model.Report;

public interface ReportService {

    void createReport(Report report, Long reservationId);
}
