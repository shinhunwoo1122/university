package com.example.university.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class MetaData {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer totalCount;
    private boolean result;
    private String code;

    @Builder.Default
    private String message = "";
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String action;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String mqIdx;
}