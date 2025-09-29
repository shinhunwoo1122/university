package com.example.university.dto;

import com.example.university.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {
    private Long id;
    private String name;
    private String studentNum;
    private Integer grade;
    private String departmentName;

    //Entity Dto로 변환 생성자
    public static StudentResponse of(Student student){
        return StudentResponse.builder()
                .id(student.getId())
                .name(student.getName())
                .studentNum(student.getStudentNum())
                .grade(student.getGrade())
                //LAZY 지연 로딩 확일을 위해 getDepartent() 호출
                .departmentName(student.getDepartment().getName())
                .build();
    }

}
