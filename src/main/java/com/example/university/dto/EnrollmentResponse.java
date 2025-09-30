package com.example.university.dto;

import com.example.university.entity.Enrollment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class EnrollmentResponse {

    private final Long id;
    private final Integer gradeScore;
    private final LocalDate enrollDate;

    // 연관된 Course 엔티티에서 가져올 정보
    private final String courseName;

    // Enrollment 엔티티를 DTO로 변환하는 of 메서드
    public static EnrollmentResponse of(Enrollment enrollment) {
        // Fetch Join 덕분에 enrollment.getCourse() 접근 시 N+1 문제 발생하지 않음
        return EnrollmentResponse.builder()
                .id(enrollment.getId())
                .gradeScore(enrollment.getGradeScore())
                .enrollDate(enrollment.getEnrollDate())
                // 연관된 Course 엔티티에서 이름을 가져옵니다.
                .courseName(enrollment.getCourse().getName())
                .build();
    }
}

