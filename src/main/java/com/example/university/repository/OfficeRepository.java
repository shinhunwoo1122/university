package com.example.university.repository;

import com.example.university.entity.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OfficeRepository extends JpaRepository<Office, Long>, QuerydslPredicateExecutor<Office> {
}
