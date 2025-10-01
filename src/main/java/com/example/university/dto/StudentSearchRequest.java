package com.example.university.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
public class StudentSearchRequest {
    private String name;
    private Integer grade;
    private String departmentName;
}
