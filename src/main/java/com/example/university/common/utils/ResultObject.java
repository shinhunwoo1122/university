package com.example.university.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultObject<T> {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private int totalCount;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private boolean isSuccess;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private boolean isEnd;
    private String code;
    private String message;
    private T data;
}
