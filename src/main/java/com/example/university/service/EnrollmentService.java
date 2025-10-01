package com.example.university.service;

import com.example.university.dto.EnrollmentBulkUpdateRequest;

public interface EnrollmentService {
    int bulkUpdateScores(EnrollmentBulkUpdateRequest request);
}
