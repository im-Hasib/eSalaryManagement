package com.example.eSalaryManagement.entity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "salary_payments")
public class SalaryPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id")
    private String employeeId;

    private String employeeName;
    private Integer grade;
    private BigDecimal basicSalary;
    private BigDecimal houseRent;
    private BigDecimal medicalAllowance;
    private BigDecimal totalSalary;
    private LocalDateTime paymentDate;
    private String status;

    // Constructors
    public SalaryPayment() {}

    public SalaryPayment(String employeeId, String employeeName, Integer grade,
                         BigDecimal basicSalary, BigDecimal houseRent, BigDecimal medicalAllowance,
                         BigDecimal totalSalary, LocalDateTime paymentDate, String status) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.grade = grade;
        this.basicSalary = basicSalary;
        this.houseRent = houseRent;
        this.medicalAllowance = medicalAllowance;
        this.totalSalary = totalSalary;
        this.paymentDate = paymentDate;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
