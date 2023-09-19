package com.example.projectboard.controller;

import com.example.projectboard.domain.constant.FormStatus;
import com.example.projectboard.domain.constant.SearchType;
import com.example.projectboard.dto.request.ArticleRequest;
import com.example.projectboard.dto.response.ArticleResponse;
import com.example.projectboard.dto.response.ArticleWithCommentsResponse;
import com.example.projectboard.dto.security.BoardPrincipal;
import com.example.projectboard.service.ArticleService;
import com.example.projectboard.service.PaginationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller
public class ArticleController {

    private final ArticleService articleService;
    private final PaginationService paginationService;

    @GetMapping
    public String articles(@RequestParam(required = false) SearchType SearchType,
                           @RequestParam(required = false) String searchValue,
                           // 10개 1페이지, 시간 내림차순
                           @PageableDefault(size = 10, sort = "createdAt",
                                   direction = Sort.Direction.DESC) Pageable pageable,
                           ModelMap map) {

        Page<ArticleResponse> article = articleService
                .searchArticles(SearchType, searchValue, pageable)
                .map(ArticleResponse::from);

        List<Integer> barNumbers = paginationService
                .getPaginationBarNumbers(pageable.getPageNumber(), article.getTotalPages());

        map.addAttribute("articles", article);
        map.addAttribute("paginationBarNumbers", barNumbers);
        map.addAttribute("searchTypes", SearchType.values());

        return "articles/index";
    }

    /**
     * 단건
     */
    @GetMapping("/{articleId}")
    public String article(@PathVariable Long articleId, ModelMap map) {

        ArticleWithCommentsResponse article = ArticleWithCommentsResponse
                .from(articleService.getArticleWithComments(articleId));

        map.addAttribute("article", article);
        map.addAttribute("articleComments", article.articleCommentsResponse());

        return "articles/detail";
    }

    @GetMapping("/search-hashtag")
    public String searchArticleHashtag(@RequestParam(required = false) String searchValue,
                                       @PageableDefault(size = 10, sort = "createdAt",
                                               direction = Sort.Direction.DESC) Pageable pageable,
                                       ModelMap map) {

        Page<ArticleResponse> articles = articleService
                .searchArticlesViaHashtag(searchValue, pageable)
                .map(ArticleResponse::from);

        List<Integer> barNumbers = paginationService
                .getPaginationBarNumbers(pageable.getPageNumber(), articles.getTotalPages());

        List<String> hashtags = articleService.getHashtags();

        map.addAttribute("articles", articles);
        map.addAttribute("hashtags", hashtags);
        map.addAttribute("paginationBarNumbers", barNumbers);
        map.addAttribute("searchType", SearchType.HASHTAG);

        return "articles/search-hashtag";
    }

    @GetMapping("/form")
    public String articleForm(ModelMap map) {
        map.addAttribute("formStatus", FormStatus.CREATE);

        return "articles/form";
    }

    @PostMapping("/form")
    public String postNewArticle(@AuthenticationPrincipal BoardPrincipal boardPrincipal,
                                 ArticleRequest articleRequest) {

        articleService.saveArticle(articleRequest.toDto(boardPrincipal.toDto()));

        return "redirect:/articles";
    }

    @GetMapping("/{articleId}/form")
    public String updateArticleForm(@PathVariable Long articleId, ModelMap map) {
        ArticleResponse article = ArticleResponse.from(articleService.getArticle(articleId));

        map.addAttribute("article", article);
        map.addAttribute("formStatus", FormStatus.UPDATE);

        return "articles/form";
    }

    @PostMapping("/{articleId}/form")
    public String updateArticle(@PathVariable Long articleId,
                                @AuthenticationPrincipal BoardPrincipal boardPrincipal,
                                ArticleRequest articleRequest) {

        articleService.updateArticle(articleId, articleRequest.toDto(boardPrincipal.toDto()));

        return "redirect:/articles/" + articleId;
    }

    @PostMapping("/{articleId}/delete")
    public String deleteArticle(@PathVariable Long articleId,
                                @AuthenticationPrincipal BoardPrincipal boardPrincipal) {

        articleService.deleteArticle(articleId, boardPrincipal.getUsername());

        return "redirect:/articles";
    }
}