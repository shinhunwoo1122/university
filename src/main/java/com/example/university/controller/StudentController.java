package com.example.university.controller;

import com.example.university.common.utils.BaseResponse;
import com.example.university.common.utils.CommonResponse;
import com.example.university.common.utils.MetaData;
import com.example.university.common.validation.ValidationGroups;
import com.example.university.dto.StudentRegistrationRequest;
import com.example.university.dto.StudentResponse;
import com.example.university.dto.StudentUpdateRequest;
import com.example.university.entity.Student;
import com.example.university.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    /**
     * Post /api/students 수강생 등록 기능
     * @param StudentRegistrationRequest request
     * @throws EntityNotFoundException
     * @Return student
     */
    @PostMapping
    public ResponseEntity<BaseResponse> registerStudent(
            @Validated(ValidationGroups.Registration.class) @RequestBody StudentRegistrationRequest request){

        Student student = studentService.registerStudent(request);

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(MetaData.builder().result(true).message("등록 완료").build(), student));
    }

    /**
     * Get /api/students/{id} 수강생 단건 조회
     * @param @PathVariable Long id
     * @throws EntityNotFoundException
     * @Return student
     */
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getStudent(@PathVariable Long id){

        Student student = studentService.findStudentById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(MetaData.builder().result(true).code("200").build(), StudentResponse.of(student)));
    }

    /**
     * Get /api/students 수강생 전체 조회
     * @throws EntityNotFoundException
     * @Return student
     */
    @GetMapping
    public ResponseEntity<BaseResponse> getAllStudents(){

        // N + 1 문제 해결을 위한 service 호출
        List<StudentResponse> students = studentService.findAllStudents();

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(MetaData.builder().result(true).code("200").message("정상적으로 조회 하였습니다.").build(), students));
    }

    /**
     * Fatch /api/students/{id}
     * @param @PathVariable id
     * @param StudentUpdateRequest request
     * @throws EntityNotFoundException
     * @return id
     */

    @PatchMapping("/{id}")
    public ResponseEntity<BaseResponse> updateStudentDetails(@PathVariable Long id, @RequestBody StudentUpdateRequest request){

        studentService.updateStudentDetails(id, request);

        return ResponseEntity.ok(new CommonResponse<>(MetaData.builder().result(true).code("200").message("수정 완료").build(), id));
    }




}
