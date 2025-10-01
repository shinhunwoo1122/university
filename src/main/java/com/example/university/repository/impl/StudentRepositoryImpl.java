package com.example.university.repository.impl;

import com.example.university.dto.StudentSearchRequest;
import com.example.university.entity.QDepartment;
import com.example.university.entity.QStudent;
import com.example.university.entity.Student;
import com.example.university.repository.StudentRepository;
import com.example.university.repository.StudentRepositoryCustom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class StudentRepositoryImpl implements StudentRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QStudent student = QStudent.student;
    private final QDepartment department = QDepartment.department;

    @Override
    public Page<Student> searchStudentDynamic(StudentSearchRequest request, Pageable pageable) {

        BooleanBuilder builder = new BooleanBuilder();
        searchCheck(request, builder);

        // 1. Querydsl 쿼리 실행
        List<Student> content =  queryFactory
                .selectFrom(student)
                .join(student.department, department)
                .fetchJoin()
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                // .orderBy(getSorts(pageable.getSort())) // 정렬 적용 로직 필요
                .fetch();

        Long total = queryFactory
                .select(student.count())
                .from(student)
                .where(builder)
                .fetchOne();

        // Null 체크 후 0으로 처리 (혹시 모를 오류 방지)
        long totalCount =  total != null ? total : 0;

        return new PageImpl<>(content, pageable, totalCount);
    }

    private void searchCheck(StudentSearchRequest request, BooleanBuilder builder) {
        // 1. 이름 조건 (StringUtils.hasText() 적용)
        if(StringUtils.hasText(request.getName())){
            builder.and(student.name.contains(request.getName()));
        }
        // 2. 학년 조건 (Integer 타입이므로 기존대로 null 체크)
        if(request.getGrade() != null){
            builder.and(student.grade.eq(request.getGrade()));
        }
        // 3. 학과명 조건 (StringUtils.hasText() 적용)
        if(StringUtils.hasText(request.getDepartmentName())){
            builder.and(student.department.name.eq(request.getDepartmentName()));
        }
    }

}
