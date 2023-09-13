package com.example.projectboard.service;

import com.example.projectboard.domain.type.SearchTtype;
import com.example.projectboard.dto.ArticleDto;
import com.example.projectboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchTtype searchTtype,
                                           String searchKeyword,
                                           Pageable pageable) {
        return Page.empty();
    }

}