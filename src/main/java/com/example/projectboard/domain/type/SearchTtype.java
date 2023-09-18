package com.example.projectboard.domain.type;

import lombok.Getter;

@Getter
public enum SearchTtype {

    TITLE("제목"),
    CONTENT("본문"),
    ID("유저 ID"),
    NICKNAME("닉네임"),
    HASHTAG("해시태그");

    private final String description;

    SearchTtype(String description) {
        this.description = description;
    }
}