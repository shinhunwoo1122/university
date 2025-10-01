package com.example.university.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EnrollmentBulkUpdateRequest {
    private String departmentName;
    private Integer scoreIncrease;
}
