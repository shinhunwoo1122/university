package com.example.university.repository;

import com.example.university.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface EnrollmentRepository extends JpaRepository<Department, Long>, QuerydslPredicateExecutor<Department> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Enrollment e SET e.gradeScore = e.gradeScore + :increase " +
            "WHERE e.student.department.name = :depName")
    int bulkUpdateScoresByDepartment(@Param("depName") String departmentName, @Param("increase") Integer scoreIncrease);
}
