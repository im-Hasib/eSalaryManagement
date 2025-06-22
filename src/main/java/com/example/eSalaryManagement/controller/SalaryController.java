package com.example.eSalaryManagement.controller;

import com.example.eSalaryManagement.dto.SalaryCalculationDto;
import com.example.eSalaryManagement.dto.SalarySummaryDto;
import com.example.eSalaryManagement.entity.SalaryPayment;
import com.example.eSalaryManagement.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/salary")
@CrossOrigin(origins = "http://localhost:4200")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @PostMapping("/set-base-salary")
    public ResponseEntity<Map<String, String>> setBaseSalary(@RequestBody Map<String, BigDecimal> request) {
        BigDecimal baseSalary = request.get("baseSalary");
        salaryService.setLowestGradeBasicSalary(baseSalary);
        return ResponseEntity.ok(Map.of("message", "Base salary set successfully"));
    }

    @GetMapping("/calculate")
    public ResponseEntity<List<SalaryCalculationDto>> calculateAllSalaries() {
        List<SalaryCalculationDto> salaries = salaryService.calculateAllSalaries();
        return ResponseEntity.ok(salaries);
    }

    @PostMapping("/process-payments")
    public ResponseEntity<SalarySummaryDto> processSalaryPayments() {
        SalarySummaryDto summary = salaryService.processSalaryPayments();
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/history")
    public ResponseEntity<List<SalaryPayment>> getSalaryHistory() {
        List<SalaryPayment> history = salaryService.getSalaryHistory();
        return ResponseEntity.ok(history);
    }

    @GetMapping("/total-paid")
    public ResponseEntity<Map<String, BigDecimal>> getTotalSalaryPaid() {
        BigDecimal total = salaryService.getTotalSalaryPaid();
        return ResponseEntity.ok(Map.of("totalPaid", total));
    }

    @PostMapping("/add-funds")
    public ResponseEntity<Map<String, String>> addFunds(@RequestBody Map<String, BigDecimal> request) {
        BigDecimal amount = request.get("amount");
        salaryService.addFundsToCompany(amount);
        return ResponseEntity.ok(Map.of("message", "Funds added successfully"));
    }
}

