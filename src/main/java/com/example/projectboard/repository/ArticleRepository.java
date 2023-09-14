package com.example.projectboard.repository;

import com.example.projectboard.domain.Article;
import com.example.projectboard.domain.QArticle;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleRepository extends JpaRepository<Article, Long>,
                                           QuerydslPredicateExecutor<Article>,
                                           QuerydslBinderCustomizer<QArticle> {

    @Override
    default void customize(QuerydslBindings bindings, QArticle root) {
        // QuerydslPredicateExecutor<Article>에 의해 모든 필드들이 열려있는 상황인데,
        // 이건 우리가 원하는게 아니다. 그렇기에 선택적으로 사용하고 싶을때 쓰는 코드
        bindings.excludeUnlistedProperties(true);

        // 원하는 필드 추가
        bindings.including(root.title, root.content, root.hashtag, root.createdAt, root.createdBy);

        // ex) "title" 필드에 대한 검색 조건을 설정하는 것 (대소문자 무시)
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}