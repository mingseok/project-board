package com.example.projectboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ArticleUpdateDto {

    private String title;
    private String content;
    private String hashtag;

    public static ArticleUpdateDto of(String title,
                                      String content,
                                      String hashtag) {

        return new ArticleUpdateDto(title, content, hashtag);
    }
}
