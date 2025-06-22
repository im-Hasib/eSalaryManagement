package com.example.eSalaryManagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;



@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @Column(name = "employee_id", length = 4)
    @Pattern(regexp = "\\d{4}", message = "Employee ID must be exactly 4 digits")
    private String employeeId;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @Min(value = 1, message = "Grade must be between 1 and 6")
    @Max(value = 6, message = "Grade must be between 1 and 6")
    private Integer grade;

    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address must not exceed 255 characters")
    private String address;

    @Pattern(regexp = "\\d{10,15}", message = "Mobile number must be 10-15 digits")
    private String mobileNumber;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_id", referencedColumnName = "id")
    @JsonManagedReference
    private BankAccount bankAccount;

    // Constructors
    public Employee() {}

    public Employee(String employeeId, String name, Integer grade, String address,
                    String mobileNumber, BankAccount bankAccount) {
        this.employeeId = employeeId;
        this.name = name;
        this.grade = grade;
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.bankAccount = bankAccount;
    }

    // Getters and Setters
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getGrade() { return grade; }
    public void setGrade(Integer grade) { this.grade = grade; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

    public BankAccount getBankAccount() { return bankAccount; }
    public void setBankAccount(BankAccount bankAccount) { this.bankAccount = bankAccount; }
}
