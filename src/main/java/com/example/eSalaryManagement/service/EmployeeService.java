package com.example.eSalaryManagement.service;

import com.example.eSalaryManagement.dto.EmployeeDto;
import com.example.eSalaryManagement.entity.BankAccount;
import com.example.eSalaryManagement.entity.Employee;
import com.example.eSalaryManagement.exception.ResourceNotFoundException;
import com.example.eSalaryManagement.exception.BusinessException;
import com.example.eSalaryManagement.repository.EmployeeRepository;
import com.example.eSalaryManagement.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    private static final Map<Integer, Integer> GRADE_LIMITS = Map.of(
            1, 1, 2, 1, 3, 2, 4, 2, 5, 2, 6, 2
    );

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAllOrderByGrade();
    }

    public Employee getEmployeeById(String id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
    }

    public Employee createEmployee(EmployeeDto employeeDto) {
        // Validate employee ID uniqueness
        if (employeeRepository.existsById(employeeDto.getEmployeeId())) {
            throw new BusinessException("Employee ID already exists: " + employeeDto.getEmployeeId());
        }

        // Validate grade limits
        validateGradeLimit(employeeDto.getGrade());

        // Validate account number uniqueness
        if (bankAccountRepository.existsByAccountNumber(employeeDto.getAccountNumber())) {
            throw new BusinessException("Account number already exists: " + employeeDto.getAccountNumber());
        }

        // Create bank account
        BankAccount bankAccount = new BankAccount(
                BankAccount.AccountType.valueOf(employeeDto.getAccountType().toUpperCase()),
                employeeDto.getAccountName(),
                employeeDto.getAccountNumber(),
                employeeDto.getCurrentBalance(),
                employeeDto.getBankName(),
                employeeDto.getBranchName()
        );

        // Create employee
        Employee employee = new Employee(
                employeeDto.getEmployeeId(),
                employeeDto.getName(),
                employeeDto.getGrade(),
                employeeDto.getAddress(),
                employeeDto.getMobileNumber(),
                bankAccount
        );

        bankAccount.setEmployee(employee);
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(String id, EmployeeDto employeeDto) {
        Employee existingEmployee = getEmployeeById(id);

        // If grade is being changed, validate the new grade limit
        if (!existingEmployee.getGrade().equals(employeeDto.getGrade())) {
            validateGradeLimit(employeeDto.getGrade());
        }

        // Update employee details
        existingEmployee.setName(employeeDto.getName());
        existingEmployee.setGrade(employeeDto.getGrade());
        existingEmployee.setAddress(employeeDto.getAddress());
        existingEmployee.setMobileNumber(employeeDto.getMobileNumber());

        // Update bank account details
        BankAccount bankAccount = existingEmployee.getBankAccount();
        bankAccount.setAccountType(BankAccount.AccountType.valueOf(employeeDto.getAccountType().toUpperCase()));
        bankAccount.setAccountName(employeeDto.getAccountName());

        // Check if account number is being changed and if it's unique
        if (!bankAccount.getAccountNumber().equals(employeeDto.getAccountNumber())) {
            if (bankAccountRepository.existsByAccountNumber(employeeDto.getAccountNumber())) {
                throw new BusinessException("Account number already exists: " + employeeDto.getAccountNumber());
            }
            bankAccount.setAccountNumber(employeeDto.getAccountNumber());
        }

        bankAccount.setCurrentBalance(employeeDto.getCurrentBalance());
        bankAccount.setBankName(employeeDto.getBankName());
        bankAccount.setBranchName(employeeDto.getBranchName());

        return employeeRepository.save(existingEmployee);
    }

    public void deleteEmployee(String id) {
        Employee employee = getEmployeeById(id);
        employeeRepository.delete(employee);
    }

    private void validateGradeLimit(Integer grade) {
        long currentCount = employeeRepository.countByGrade(grade);
        int limit = GRADE_LIMITS.get(grade);

        if (currentCount >= limit) {
            throw new BusinessException("Grade " + grade + " has reached its maximum limit of " + limit + " employees");
        }
    }

    public List<Employee> getEmployeesByGrade(Integer grade) {
        return employeeRepository.findByGradeOrderByEmployeeId(grade);
    }
}

