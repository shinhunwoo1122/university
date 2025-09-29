package com.example.university.repository;

import com.example.university.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProfessorRepository extends JpaRepository<Professor, Long>, QuerydslPredicateExecutor<Professor> {
}
