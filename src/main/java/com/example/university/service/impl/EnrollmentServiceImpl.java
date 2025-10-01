package com.example.university.service.impl;

import com.example.university.dto.EnrollmentBulkUpdateRequest;
import com.example.university.repository.EnrollmentRepository;
import com.example.university.service.EnrollmentService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final EntityManager em;

    @Transactional
    @Override
    public int bulkUpdateScores(EnrollmentBulkUpdateRequest request) {

        int updateCount = enrollmentRepository.bulkUpdateScoresByDepartment(request.getDepartmentName(), request.getScoreIncrease());

        return updateCount;
    }
}
