package com.fishbook.reports.controller;

import com.fishbook.exception.ApiRequestException;
import com.fishbook.reports.dto.AdminResponseOnSellerReportDto;
import com.fishbook.reports.dto.BuyerReportDetailsDto;
import com.fishbook.reports.dto.BuyerReportDto;
import com.fishbook.reports.dto.BuyerReportResponseDto;
import com.fishbook.reports.dto.CreateReportDto;
import com.fishbook.reports.dto.SellerReportPreviewDto;
import com.fishbook.reports.model.BuyerReport;
import com.fishbook.reports.model.Report;
import com.fishbook.reports.service.ReportService;
import com.fishbook.user.dto.DeleteResponseDto;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    @PreAuthorize("hasAnyRole('BOAT_OWNER', 'HOUSE_OWNER', 'INSTRUCTOR')")
    public ResponseEntity<?> createReport(@Valid @RequestBody CreateReportDto dto) {
        reportService.createReport(Report.builder()
                .comment(dto.getComment())
                .clientArrived(dto.getClientArrived())
                .shouldGetPenalty(dto.getShouldGetPenalty())
                .build(), dto.getReservationId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/buyers")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity createBuyerReport(@Valid @RequestBody BuyerReportDto buyerReportDto, Principal principal){
        try{
            reportService.createBuyerReport(buyerReportDto.getMessage(), buyerReportDto.getReservationId(), principal.getName());
            return new ResponseEntity(buyerReportDto, HttpStatus.CREATED);
        } catch (ApiRequestException e){
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/buyers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllBuyerReports() {
        List<BuyerReportDetailsDto> reports = reportService.getAllBuyerReports().stream()
                .map(buyerReport -> new BuyerReportDetailsDto(buyerReport.getId(), buyerReport.getComment(), buyerReport.getReservation().getId(),
                        buyerReport.getReservation().getEntity().getOwner().getEmail(), buyerReport.getReservation().getEntity().getOwner().getPhoneNumber(),
                        buyerReport.getReservation().getClient().getEmail(), buyerReport.getReservation().getClient().getPhoneNumber()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @PutMapping(value = "/buyers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity respondToBuyerReport(@RequestBody BuyerReportResponseDto dto) throws InterruptedException {
        reportService.respondToBuyerReport(dto.getReportId(), dto.getResponse());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/seller")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity getSellerReports(){
        List<Report> reports = reportService.getSellerReports();
        List<SellerReportPreviewDto> sellerReportPreviewDtos = reports.stream().map(r -> new SellerReportPreviewDto(r.getId(), r.getReservation().getEntity().getOwner().getEmail(), r.getReservation().getClient().getEmail(),
                r.getClientArrived(), r.getShouldGetPenalty(), r.getComment())).collect(Collectors.toList());
        return new ResponseEntity(sellerReportPreviewDtos, HttpStatus.OK);
    }

    @PostMapping(value = "/seller/response")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity createResponseOnSellerReport(@RequestBody AdminResponseOnSellerReportDto res){
        try {
            reportService.createResponseOnSellerReport(res.getReportId(), res.getSellerMessage(), res.getBuyerMessage(),
                    res.getGivePenalty(), res.getSellerEmail(), res.getBuyerEmail(), res.getBuyerArrived());
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (InterruptedException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ApiRequestException e){
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }

    }
}
