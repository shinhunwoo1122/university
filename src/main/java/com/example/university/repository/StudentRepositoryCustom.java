package com.example.university.repository;

import com.example.university.dto.StudentSearchRequest;
import com.example.university.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentRepositoryCustom {
    Page<Student> searchStudentDynamic(StudentSearchRequest request, Pageable pageable);
}
