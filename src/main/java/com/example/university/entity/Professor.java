package com.example.university.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

/**
 *
 * @FileName	: Professor.java
 * @Project		: university
 * @Date		: 2025.09.29
 * @Writter		: shinhunwoo
 *
 * @Description
 * 교수 Entity
 * 1:1 관계의 비주인이며, N:1 관계의 주인입니다.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "professor")
@ToString(exclude = {"department", "office"})
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prof_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "hire_date",nullable = false)
    private String hireDate;

    //N:1 관계의 주인
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    @JsonIgnore
    private Department department;

    //1:1 관계의 비주인 (mappedBy로 office가 주인임을 명시)
    //LAZY 로딩으로 설정하여 N+1 문제 방지
    @OneToOne(mappedBy = "professor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Office office;

    @Builder
    public Professor(Long id, String name, String hireDate) {
        this.name = name;
        this.hireDate = hireDate;
    }
}
