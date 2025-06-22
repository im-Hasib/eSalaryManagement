package com.example.eSalaryManagement.repository;

import com.example.eSalaryManagement.entity.SalaryPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SalaryPaymentRepository extends JpaRepository<SalaryPayment, Long> {
    List<SalaryPayment> findByEmployeeIdOrderByPaymentDateDesc(String employeeId);

    @Query("SELECT SUM(sp.totalSalary) FROM SalaryPayment sp")
    BigDecimal getTotalSalaryPaid();

    List<SalaryPayment> findAllByOrderByGradeAscEmployeeNameAsc();
}
