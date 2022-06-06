package com.fishbook.reports.service;

import com.fishbook.reports.model.BuyerReport;
import com.fishbook.reports.model.Report;

import java.util.List;

public interface ReportService {

    void createReport(Report report, Long reservationId);
    void createBuyerReport(String message, Long reservationId, String email);
    List<BuyerReport> getAllBuyerReports();
    void respondToBuyerReport(Long id, String response) throws InterruptedException;
}
