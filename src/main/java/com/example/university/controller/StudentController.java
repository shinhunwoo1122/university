package com.example.university.controller;

import com.example.university.common.utils.BaseResponse;
import com.example.university.common.utils.CommonResponse;
import com.example.university.common.utils.MetaData;
import com.example.university.common.validation.ValidationGroups;
import com.example.university.dto.StudentRegistrationRequest;
import com.example.university.dto.StudentResponse;
import com.example.university.entity.Student;
import com.example.university.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<BaseResponse> registerStudent(
            @Validated(ValidationGroups.Registration.class) @RequestBody StudentRegistrationRequest request){

        Student student = studentService.registerStudent(request);

        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse<>(MetaData.builder().result(true).message("등록 완료").build(), student));
    }

}
