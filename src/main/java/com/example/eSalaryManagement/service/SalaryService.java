package com.example.eSalaryManagement.service;

import com.example.eSalaryManagement.dto.SalaryCalculationDto;
import com.example.eSalaryManagement.dto.SalarySummaryDto;
import com.example.eSalaryManagement.entity.Employee;
import com.example.eSalaryManagement.entity.CompanyAccount;
import com.example.eSalaryManagement.entity.SalaryPayment;
import com.example.eSalaryManagement.exception.BusinessException;
import com.example.eSalaryManagement.repository.EmployeeRepository;
import com.example.eSalaryManagement.repository.CompanyAccountRepository;
import com.example.eSalaryManagement.repository.SalaryPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Service
@Transactional
public class SalaryService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyAccountRepository companyAccountRepository;

    @Autowired
    private SalaryPaymentRepository salaryPaymentRepository;

    private BigDecimal lowestGradeBasicSalary = BigDecimal.ZERO;

    public void setLowestGradeBasicSalary(BigDecimal salary) {
        this.lowestGradeBasicSalary = salary;
    }

    public BigDecimal calculateBasicSalaryByGrade(Integer grade) {
        // Grade 6 is the lowest, Grade 1 is the highest
        int gradeFromLowest = 7 - grade; // Convert to position from lowest (6->1, 5->2, etc.)
        BigDecimal additionalAmount = new BigDecimal(5000).multiply(new BigDecimal(gradeFromLowest - 1));
        return lowestGradeBasicSalary.add(additionalAmount);
    }

    public SalaryCalculationDto calculateSalary(Employee employee) {
        BigDecimal basicSalary = calculateBasicSalaryByGrade(employee.getGrade());
        BigDecimal houseRent = basicSalary.multiply(new BigDecimal("0.20")).setScale(2, RoundingMode.HALF_UP);
        BigDecimal medicalAllowance = basicSalary.multiply(new BigDecimal("0.15")).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalSalary = basicSalary.add(houseRent).add(medicalAllowance);

        return new SalaryCalculationDto(
                employee.getEmployeeId(),
                employee.getName(),
                employee.getGrade(),
                basicSalary,
                houseRent,
                medicalAllowance,
                totalSalary
        );
    }

    public List<SalaryCalculationDto> calculateAllSalaries() {
        List<Employee> employees = employeeRepository.findAllOrderByGrade();
        List<SalaryCalculationDto> salaryCalculations = new ArrayList<>();

        for (Employee employee : employees) {
            salaryCalculations.add(calculateSalary(employee));
        }

        return salaryCalculations;
    }

    public SalarySummaryDto processSalaryPayments() {
        List<Employee> employees = employeeRepository.findAllOrderByGrade();
        List<SalaryCalculationDto> salaryDetails = new ArrayList<>();
        List<SalaryPayment> payments = new ArrayList<>();

        CompanyAccount companyAccount = getCompanyAccount();
        BigDecimal totalSalaryPaid = BigDecimal.ZERO;
        String status = "SUCCESS";
        String message = "All salaries processed successfully";

        for (Employee employee : employees) {
            SalaryCalculationDto salaryCalc = calculateSalary(employee);

            if (companyAccount.getCurrentBalance().compareTo(salaryCalc.getTotalSalary()) >= 0) {
                // Sufficient funds - process payment
                companyAccount.setCurrentBalance(
                        companyAccount.getCurrentBalance().subtract(salaryCalc.getTotalSalary())
                );

                // Update employee bank account
                employee.getBankAccount().setCurrentBalance(
                        employee.getBankAccount().getCurrentBalance().add(salaryCalc.getTotalSalary())
                );

                // Record payment
                SalaryPayment payment = new SalaryPayment(
                        employee.getEmployeeId(),
                        employee.getName(),
                        employee.getGrade(),
                        salaryCalc.getBasicSalary(),
                        salaryCalc.getHouseRent(),
                        salaryCalc.getMedicalAllowance(),
                        salaryCalc.getTotalSalary(),
                        LocalDateTime.now(),
                        "PAID"
                );
                payments.add(payment);
                salaryDetails.add(salaryCalc);
                totalSalaryPaid = totalSalaryPaid.add(salaryCalc.getTotalSalary());

            } else {
                // Insufficient funds
                status = "INSUFFICIENT_FUNDS";
                message = "Insufficient company balance to pay salary for employee: " + employee.getName() +
                        ". Required: " + salaryCalc.getTotalSalary() + ", Available: " + companyAccount.getCurrentBalance();
                break;
            }
        }

        // Save all changes
        companyAccountRepository.save(companyAccount);
        employeeRepository.saveAll(employees);
        salaryPaymentRepository.saveAll(payments);

        return new SalarySummaryDto(
                salaryDetails,
                totalSalaryPaid,
                companyAccount.getCurrentBalance(),
                status,
                message
        );
    }

    public CompanyAccount getCompanyAccount() {
        List<CompanyAccount> accounts = companyAccountRepository.findAll();
        if (accounts.isEmpty()) {
            throw new BusinessException("No company account found. Please set up company account first.");
        }
        return accounts.get(0);
    }

    public CompanyAccount addFundsToCompany(BigDecimal amount) {
        CompanyAccount companyAccount = getCompanyAccount();
        companyAccount.setCurrentBalance(companyAccount.getCurrentBalance().add(amount));
        return companyAccountRepository.save(companyAccount);
    }

    public List<SalaryPayment> getSalaryHistory() {
        return salaryPaymentRepository.findAllByOrderByGradeAscEmployeeNameAsc();
    }

    public BigDecimal getTotalSalaryPaid() {
        BigDecimal total = salaryPaymentRepository.getTotalSalaryPaid();
        return total != null ? total : BigDecimal.ZERO;
    }
}
