package com.example.university.repository;

import com.example.university.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface EnrollmentRepository extends JpaRepository<Department, Long>, QuerydslPredicateExecutor<Department> {
}
