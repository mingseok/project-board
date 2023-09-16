package com.example.projectboard.controller;

import com.example.projectboard.domain.type.SearchTtype;
import com.example.projectboard.dto.response.ArticleResponse;
import com.example.projectboard.dto.response.ArticleWithCommentsResponse;
import com.example.projectboard.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public String articles(@RequestParam(required = false) SearchTtype searchTtype,
                           @RequestParam(required = false) String searchValue,
                           @PageableDefault(size = 10, sort = "createAt",
                                   direction = Sort.Direction.DESC) Pageable pageable,
                           ModelMap map) {

        map.addAttribute("articles",
                        articleService.searchArticles(searchTtype, searchValue, pageable)
                        .map(ArticleResponse::from));

        return "articles/index";
    }

    /**
     * 단건
     */
    @GetMapping("/{articleId}")
    public String article(@PathVariable Long articleId, ModelMap map) {
        ArticleWithCommentsResponse article = ArticleWithCommentsResponse.from(articleService.getArticle(articleId));
        map.addAttribute("article", article);
        map.addAttribute("articleComments", article.articleCommentsResponse());

        return "articles/detail";
    }
}