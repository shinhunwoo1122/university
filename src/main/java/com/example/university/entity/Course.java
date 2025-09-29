package com.example.university.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @FileName	: Course.java
 * @Project		: university
 * @Date		: 2025.09.29
 * @Writter		: shinhunwoo
 *
 * @Description
 * 강좌 Entity
 * @ManyToOne 관계의 주인을 참조하며, N:M 관계 해소 테이블인 Enrollment의 비주인입니다.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "course")
@ToString(exclude = {"professor", "enrollments"})
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "credit", nullable = false)
    private Integer credit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prof_id")
    @JsonIgnore
    private Professor professor;

    @OneToMany(mappedBy = "course")
    @JsonIgnore
    private List<Enrollment> enrollments = new ArrayList<>();
}
