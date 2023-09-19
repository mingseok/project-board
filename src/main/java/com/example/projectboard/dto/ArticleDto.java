package com.example.projectboard.dto;

import com.example.projectboard.domain.Article;
import com.example.projectboard.domain.UserAccount;

import java.time.LocalDateTime;

/**
 * record는 게터, 세터를 알아서 만들어주고
 * 다른 클래스에서 불러 사용할때도, 게터,세터를 생략해서 보여준다
 * 즉, 'getId' -> 'id' 로만 출력됨 (생략)
 */
public record ArticleDto(Long id,
                         UserAccountDto userAccountDto,
                         String title,
                         String content,
                         String hashtag,
                         LocalDateTime createdAt,
                         String createdBy,
                         LocalDateTime modifiedAt,
                         String modifiedBy) {

    public static ArticleDto of(UserAccountDto userAccountDto,
                                String title,
                                String content,
                                String hashtag) {

        return new ArticleDto(null,
                userAccountDto,
                title,
                content,
                hashtag,
                null,
                null,
                null,
                null);
    }


    public static ArticleDto of(Long id,
                                UserAccountDto userAccountDto,
                                String title,
                                String content,
                                String hashtag,
                                LocalDateTime createdAt,
                                String createdBy,
                                LocalDateTime modifiedAt,
                                String modifiedBy) {

        return new ArticleDto(id,
                userAccountDto,
                title,
                content,
                hashtag,
                createdAt,
                createdBy,
                modifiedAt,
                modifiedBy);
    }

    public static ArticleDto from(Article entity) {
        return new ArticleDto(entity.getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtag(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy());
    }

    public Article toEntity(UserAccount userAccount) {
        return Article.of(userAccount, title, content, hashtag);
    }
}