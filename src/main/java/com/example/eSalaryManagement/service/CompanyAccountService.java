package com.example.eSalaryManagement.service;

import com.example.eSalaryManagement.entity.CompanyAccount;
import com.example.eSalaryManagement.exception.ResourceNotFoundException;
import com.example.eSalaryManagement.repository.CompanyAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class CompanyAccountService {

    @Autowired
    private CompanyAccountRepository companyAccountRepository;

    public List<CompanyAccount> getAllCompanyAccounts() {
        return companyAccountRepository.findAll();
    }       

    public CompanyAccount getCompanyAccount() {
        List<CompanyAccount> accounts = companyAccountRepository.findAll();
        if (accounts.isEmpty()) {
            throw new ResourceNotFoundException("No company account found");
        }
        return accounts.get(0);
    }

    public CompanyAccount createCompanyAccount(CompanyAccount companyAccount) {
        // Delete existing accounts if any (only one company account allowed)
        companyAccountRepository.deleteAll();
        return companyAccountRepository.save(companyAccount);
    }

    public CompanyAccount updateCompanyAccount(Long id, CompanyAccount companyAccount) {
        CompanyAccount existingAccount = companyAccountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company account not found with ID: " + id));

        existingAccount.setAccountName(companyAccount.getAccountName());
        existingAccount.setAccountNumber(companyAccount.getAccountNumber());
        existingAccount.setCurrentBalance(companyAccount.getCurrentBalance());
        existingAccount.setBankName(companyAccount.getBankName());
        existingAccount.setBranchName(companyAccount.getBranchName());

        return companyAccountRepository.save(existingAccount);
    }

    public CompanyAccount addFunds(BigDecimal amount) {
        CompanyAccount account = getCompanyAccount();
        account.setCurrentBalance(account.getCurrentBalance().add(amount));
        return companyAccountRepository.save(account);
    }

    public void deleteCompanyAccount(Long id) {
        CompanyAccount account = companyAccountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company account not found with ID: " + id));
        companyAccountRepository.delete(account);
    }
}


