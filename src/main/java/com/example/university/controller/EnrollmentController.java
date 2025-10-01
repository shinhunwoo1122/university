package com.example.university.controller;

import com.example.university.common.utils.BaseResponse;
import com.example.university.common.utils.CommonResponse;
import com.example.university.common.utils.MetaData;
import com.example.university.dto.EnrollmentBulkUpdateRequest;
import com.example.university.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PatchMapping("/bulk-score")
    public ResponseEntity<BaseResponse> bulkUpdateScore(@RequestBody EnrollmentBulkUpdateRequest request){

        int updateCount = enrollmentService.bulkUpdateScores(request);

        return ResponseEntity.ok(new CommonResponse<>(MetaData.builder().result(true).code("201").message("전체 수정 완료").build(), "변경 건수 : " + updateCount));
    }


}
