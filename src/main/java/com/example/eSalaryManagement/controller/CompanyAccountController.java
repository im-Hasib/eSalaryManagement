package com.example.eSalaryManagement.controller;

import com.example.eSalaryManagement.entity.CompanyAccount;
import com.example.eSalaryManagement.service.CompanyAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/company-account")
@CrossOrigin(origins = "http://localhost:4200")
public class CompanyAccountController {

    @Autowired
    private CompanyAccountService companyAccountService;

    @GetMapping
    public ResponseEntity<List<CompanyAccount>> getAllCompanyAccounts() {
        List<CompanyAccount> accounts = companyAccountService.getAllCompanyAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/current")
    public ResponseEntity<CompanyAccount> getCurrentCompanyAccount() {
        CompanyAccount account = companyAccountService.getCompanyAccount();
        return ResponseEntity.ok(account);
    }

    @PostMapping
    public ResponseEntity<CompanyAccount> createCompanyAccount(@Valid @RequestBody CompanyAccount companyAccount) {
        CompanyAccount account = companyAccountService.createCompanyAccount(companyAccount);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyAccount> updateCompanyAccount(@PathVariable Long id,
                                                               @Valid @RequestBody CompanyAccount companyAccount) {
        CompanyAccount account = companyAccountService.updateCompanyAccount(id, companyAccount);
        return ResponseEntity.ok(account);
    }

    @PostMapping("/add-funds")
    public ResponseEntity<CompanyAccount> addFunds(@RequestBody Map<String, BigDecimal> request) {
        BigDecimal amount = request.get("amount");
        CompanyAccount account = companyAccountService.addFunds(amount);
        return ResponseEntity.ok(account);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompanyAccount(@PathVariable Long id) {
        companyAccountService.deleteCompanyAccount(id);
        return ResponseEntity.noContent().build();
    }
}
