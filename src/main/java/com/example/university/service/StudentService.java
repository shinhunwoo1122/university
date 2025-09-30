package com.example.university.service;

import com.example.university.dto.StudentRegistrationRequest;
import com.example.university.dto.StudentResponse;
import com.example.university.dto.StudentUpdateRequest;
import com.example.university.entity.Student;

import java.util.List;

public interface StudentService {
    Student registerStudent(StudentRegistrationRequest request);

    Student findStudentById(Long id);

    List<StudentResponse> findAllStudents();

    void updateStudentDetails(Long id, StudentUpdateRequest request);
}
