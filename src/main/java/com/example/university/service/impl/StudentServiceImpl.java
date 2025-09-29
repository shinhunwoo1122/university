package com.example.university.service.impl;

import com.example.university.dto.StudentRegistrationRequest;
import com.example.university.entity.Department;
import com.example.university.entity.Student;
import com.example.university.repository.DepartmentRepository;
import com.example.university.repository.StudentRepository;
import com.example.university.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;

    @Transactional
    @Override
    public Student registerStudent(StudentRegistrationRequest request) {
        //1. 학과 엔티티 조회 (LAZY 로딩이므로 조회 쿼리)
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException("해당 학과 ID 조회 실패 : " + request.getDepartmentId()));


        //2. student 엔티티 생성
        Student student = Student.builder()
                .name(request.getName())
                .studentNum(request.getStudentNum())
                .grade(request.getGrade())
                .department(department)
                .build();

        //3. student 저장
        studentRepository.save(student);


        return student;
    }
}
