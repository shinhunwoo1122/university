package com.example.university.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @FileName	: Student.java
 * @Project		: university
 * @Date		: 2025.09.29
 * @Writter		: shinhunwoo
 *
 * @Description
 * 학생 Entity
 * @ManyToOne 관계의 주인이며, N:M 관계 해소 테이블인 Enrollment를 참조합니다.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "student")
@ToString(exclude = {"department", "enrollments"})
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;

    @Column(name =" name", nullable = false)
    private String name;

    @Column(name = "student_num", nullable = false, unique = true)
    private String studentNum;

    @Column(name = "grade", nullable = false)
    private Integer grade;

    //N:1 관계의 주인
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id", nullable = false)
    @JsonIgnore
    private Department department;

    //1:N 관계 (Enrollment 쪽이 주인)
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Enrollment> enrollments = new ArrayList<>();

    @Builder
    public Student(Long id, String name, String studentNum, Department department, Integer grade) {
        this.name = name;
        this.studentNum = studentNum;
        this.department = department;
        this.grade = grade;
    }
}
