package com.example.university.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
/**
 *
 * @FileName	: office.java
 * @Project		: university
 * @Date		: 2025.09.29
 * @Writter		: shinhunwoo
 *
 * @Description
 * 강의(연구실) Entity
 * 1:1 관계의 주인이며, @MapsId를 사용하여 Professor의 PK를 FK로 공유합니다.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "office")
@ToString(exclude = {"professor"})
public class Office {

    @Id
    @Column(name = "prof_id")
    private Long id;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "tel_num")
    private String telNum;

    //1:1 관계의 주인 (Professor의 ID를 받기 위해 LAZY설정)
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId // Professor의 ID를 이 엔티티 ID로 매핑
    @JoinColumn(name = "prof_id") // 외래 키 컬럼 이름 명시
    @JsonIgnore
    private Professor professor;



}
