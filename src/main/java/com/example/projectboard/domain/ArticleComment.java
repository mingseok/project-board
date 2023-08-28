package com.example.projectboard.domain;

import java.time.LocalDateTime;

/**
 * 댓글
 */
public class ArticleComment {

    private Long id;
    private Article article;
    private String content; // 내용

    private LocalDateTime createdAt; // 생성일시
    private String createdBy; // 생성자
    private LocalDateTime modifiedAt; // 수정 일시
    private String modifiedBy; // 수정자

}