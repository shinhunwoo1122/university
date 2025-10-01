package com.example.university.dto.common;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
public class PageResponse<T> {
    /* 데이터 리스트 범용성을 위해 object */
    private final List<T> content;
    /* 전체 페이지 수 */
    private final int totalPages;
    /* 전체 데이터 갯수 */
    private final long totalElements;
    /* 현재 페이지 번호 */
    private final int currentPage;
    /* 첫 페이지 여부 */
    private final boolean isFirst;
    /* 마지막 페이지 여부 */
    private final boolean isLast;

    public static <T> PageResponse<T> of(Page<T> page){
        return PageResponse.<T>builder()
                .content(page.getContent())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .currentPage(page.getNumber())
                .isFirst(page.isFirst())
                .isLast(page.isLast())
                .build();
    }
}
