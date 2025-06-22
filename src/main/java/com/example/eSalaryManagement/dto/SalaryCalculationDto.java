package com.example.eSalaryManagement.dto;

import java.math.BigDecimal;

public class SalaryCalculationDto {
    private String employeeId;
    private String employeeName;
    private Integer grade;
    private BigDecimal basicSalary;
    private BigDecimal houseRent;
    private BigDecimal medicalAllowance;
    private BigDecimal totalSalary;

    // Constructors
    public SalaryCalculationDto() {}

    public SalaryCalculationDto(String employeeId, String employeeName, Integer grade,
                                BigDecimal basicSalary, BigDecimal houseRent,
                                BigDecimal medicalAllowance, BigDecimal totalSalary) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.grade = grade;
        this.basicSalary = basicSalary;
        this.houseRent = houseRent;
        this.medicalAllowance = medicalAllowance;
        this.totalSalary = totalSalary;
    }

    // Getters and Setters
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public Integer getGrade() { return grade; }
    public void setGrade(Integer grade) { this.grade = grade; }

    public BigDecimal getBasicSalary() { return basicSalary; }
    public void setBasicSalary(BigDecimal basicSalary) { this.basicSalary = basicSalary; }

    public BigDecimal getHouseRent() { return houseRent; }
    public void setHouseRent(BigDecimal houseRent) { this.houseRent = houseRent; }

    public BigDecimal getMedicalAllowance() { return medicalAllowance; }
    public void setMedicalAllowance(BigDecimal medicalAllowance) { this.medicalAllowance = medicalAllowance; }

    public BigDecimal getTotalSalary() { return totalSalary; }
    public void setTotalSalary(BigDecimal totalSalary) { this.totalSalary = totalSalary; }
}
