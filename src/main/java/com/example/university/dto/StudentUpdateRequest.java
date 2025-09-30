package com.example.university.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentUpdateRequest {

    private String name;

    private Integer grade;

    private Long departmentId;

}
