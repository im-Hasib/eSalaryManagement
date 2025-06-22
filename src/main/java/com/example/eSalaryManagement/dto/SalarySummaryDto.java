package com.example.eSalaryManagement.dto;

import java.math.BigDecimal;
import java.util.List;

public class SalarySummaryDto {
    private List<SalaryCalculationDto> salaryDetails;
    private BigDecimal totalSalaryPaid;
    private BigDecimal remainingCompanyBalance;
    private String status;
    private String message;

    // Constructors
    public SalarySummaryDto() {}

    public SalarySummaryDto(List<SalaryCalculationDto> salaryDetails, BigDecimal totalSalaryPaid,
                            BigDecimal remainingCompanyBalance, String status, String message) {
        this.salaryDetails = salaryDetails;
        this.totalSalaryPaid = totalSalaryPaid;
        this.remainingCompanyBalance = remainingCompanyBalance;
        this.status = status;
        this.message = message;
    }

    // Getters and Setters
    public List<SalaryCalculationDto> getSalaryDetails() { return salaryDetails; }
    public void setSalaryDetails(List<SalaryCalculationDto> salaryDetails) { this.salaryDetails = salaryDetails; }

    public BigDecimal getTotalSalaryPaid() { return totalSalaryPaid; }
    public void setTotalSalaryPaid(BigDecimal totalSalaryPaid) { this.totalSalaryPaid = totalSalaryPaid; }

    public BigDecimal getRemainingCompanyBalance() { return remainingCompanyBalance; }
    public void setRemainingCompanyBalance(BigDecimal remainingCompanyBalance) { this.remainingCompanyBalance = remainingCompanyBalance; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
