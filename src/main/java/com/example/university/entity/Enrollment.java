package com.example.university.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
/**
 *
 * @FileName	: Enrollment.java
 * @Project		: university
 * @Date		: 2025.09.29
 * @Writter		: shinhunwoo
 *
 * @Description
 * 강좌 Entity
 * N:M 관계를 해소하는 중간 엔티티이자, 모든 N:1 관계의 주인입니다.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
@ToString(exclude = {"student", "course"})
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enroll_id")
    private Long id;

    // N:1 관계의 주인
    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    //N:1 관계의 주인
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIgnore
    private Course course;

    @Column(name = "grade_score")
    private Integer gradeScore;

    @Column(name = "enroll_date", nullable = false)
    private LocalDate enrollDate;
}
