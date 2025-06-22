package com.example.eSalaryManagement.repository;

import com.example.eSalaryManagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    List<Employee> findByGradeOrderByEmployeeId(Integer grade);

    @Query("SELECT COUNT(e) FROM Employee e WHERE e.grade = :grade")
    long countByGrade(Integer grade);

    @Query("SELECT e FROM Employee e ORDER BY e.grade ASC")
    List<Employee> findAllOrderByGrade();
}

