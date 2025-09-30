package com.example.university.exception;

import com.example.university.common.utils.BaseResponse;
import com.example.university.common.utils.CommonResponse;
import com.example.university.common.utils.ErrorResponse;
import com.example.university.common.utils.MetaData;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<BaseResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("요청 변수 타입이 일치하지 않습니다."));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<BaseResponse> handleEntityNotFound(EntityNotFoundException ex){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage(), "400"));
    }
}
