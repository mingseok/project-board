package com.example.projectboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ArticleDto {

    private String title;
    private String content;
    private String hashtag;

    private LocalDateTime createdAt;
    private String createdBy;

    public static ArticleDto of(String title, String content, String hashtag, LocalDateTime createdAt, String createdBy) {
        return new ArticleDto(title, content, hashtag, createdAt, createdBy);
    }
}
