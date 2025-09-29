package com.example.university.dto;

import com.example.university.common.validation.ValidationGroups;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentRegistrationRequest {
    /*이름*/
    @NotBlank(groups = {ValidationGroups.Registration.class}, message = "이름은 필수 입력 항목입니다.")
    private String name;
    /*학번*/
    @NotBlank(groups = {ValidationGroups.Registration.class}, message = "학번은 필수 입력 항목입니다.")
    private String studentNum;
    /*학년*/
    @NotNull(groups = {ValidationGroups.Registration.class}, message = "학년은 필수 입력 항목입니다.")
    @Min(groups = {ValidationGroups.Registration.class}, value = 1, message = "학년은 1학년 이상이여야 합니다.")
    private Integer grade;
    /*학과*/
    @NotNull(groups = {ValidationGroups.Registration.class}, message = "학과 ID는 필수 입력 항목입니다.")
    private Long departmentId;
}
