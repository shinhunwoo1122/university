package com.example.university.repository;

import com.example.university.dto.StudentSearchRequest;
import com.example.university.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long>,
        QuerydslPredicateExecutor<Student>,
        StudentRepositoryCustom{

    @Query("SELECT s FROM Student s " +
            "JOIN FETCH s.department d " +
            "LEFT JOIN FETCH s.enrollments e " +
            "LEFT JOIN FETCH e.course c")
    List<Student> findAllWithDetailas();

}
