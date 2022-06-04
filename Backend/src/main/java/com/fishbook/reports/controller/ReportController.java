package com.fishbook.reports.controller;

import com.fishbook.reports.dto.CreateReportDto;
import com.fishbook.reports.model.Report;
import com.fishbook.reports.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    @PreAuthorize("hasAnyRole('BOAT_OWNER', 'HOUSE_OWNER', 'INSTRUCTOR')")
    public ResponseEntity<?> createReport(@RequestBody CreateReportDto dto) {
        reportService.createReport(Report.builder()
                .comment(dto.getComment())
                .clientArrived(dto.getClientArrived())
                .shouldGetPenalty(dto.getShouldGetPenalty())
                .build(), dto.getReservationId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
