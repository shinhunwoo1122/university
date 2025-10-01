package com.example.university.repository;

import com.example.university.dto.StudentSearchRequest;
import com.example.university.entity.Student;

import java.util.List;

public interface StudentRepositoryCustom {
    List<Student> searchStudentDynamic(StudentSearchRequest request);
}
