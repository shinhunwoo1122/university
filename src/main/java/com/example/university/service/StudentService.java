package com.example.university.service;

import com.example.university.dto.StudentRegistrationRequest;
import com.example.university.entity.Student;

public interface StudentService {
    Student registerStudent(StudentRegistrationRequest request);
}
