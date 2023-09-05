package com.example.projectboard.service;

import com.example.projectboard.domain.type.SearchTtype;
import com.example.projectboard.dto.ArticleDto;
import com.example.projectboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public List<ArticleDto> searchArticles(SearchTtype title, String search_keyword) {
        return List.of();
    }




}
