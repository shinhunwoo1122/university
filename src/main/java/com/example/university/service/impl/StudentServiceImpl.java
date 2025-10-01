package com.example.university.service.impl;

import com.example.university.dto.StudentRegistrationRequest;
import com.example.university.dto.StudentResponse;
import com.example.university.dto.StudentSearchRequest;
import com.example.university.dto.StudentUpdateRequest;
import com.example.university.dto.common.PageResponse;
import com.example.university.entity.Department;
import com.example.university.entity.Student;
import com.example.university.repository.DepartmentRepository;
import com.example.university.repository.StudentRepository;
import com.example.university.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public Student findStudentById(Long id) {

        return studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("조회 실패 하였습니다. parameter = " + id));
    }

    @Override
    public List<StudentResponse> findAllStudents() {

        //Fetch Join 사용하여 데이터를 한번에 가져옴
        List<Student> students = studentRepository.findAllWithDetailas();

        return students.stream().map(StudentResponse::of).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void updateStudentDetails(Long id, StudentUpdateRequest request) {

        Student student = studentRepository.findById(id).orElseThrow(() ->new EntityNotFoundException("해당 ID의 학생을 찾을 수 없습니다.."));
        Department department = null;

        if(request.getDepartmentId() != null){

            department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(() -> new EntityNotFoundException("새 학과 ID를 찾을 수 없습니다."));
            student.updateDetails(request, department);

            // 4. save() 호출 없이, 트랜잭션 커밋 시 변경된 필드만 UPDATE
            log.info("Student ID {} details updated via Dirty Checking.", id);
        }
    }

    @Override
    public PageResponse<StudentResponse> searchStudents(StudentSearchRequest request, Pageable pageable) {

        Page<Student> students = studentRepository.searchStudentDynamic(request, pageable);

        Page<StudentResponse> dtoPage = students.map(StudentResponse::of);

        return PageResponse.of(dtoPage);
    }


}
