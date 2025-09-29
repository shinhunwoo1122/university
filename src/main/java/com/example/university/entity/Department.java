package com.example.university.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @FileName	: Department.java
 * @Project		: university
 * @Date		: 2025.09.29
 * @Writter		: shinhunwoo
 *
 * @Description
 * 과목(학과) Entity
 * 1:N 관계의 비주인이며, 학생과 교수를 조회하는 용도로 사용됩니다.
 */
@Entity
@Table(name = "department")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"students", "professors"})
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="dept_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    //양방향 매핑 : Department 1 : N student(비주인)
    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private List<Student> students = new ArrayList<>();

    //양방향 매핑 Department 1 : N Professor(비주인)
    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private List<Professor> professors = new ArrayList<>();

    @Builder
    public Department(String location, Long id) {
        this.location = location;
        this.id = id;
    }
}
