package es.upm.spring_practice.adapters.jpa.shop.persistence;


import es.upm.spring_practice.adapters.jpa.shop.daos.ArticleRepository;
import es.upm.spring_practice.adapters.jpa.shop.entities.ArticleEntity;
import es.upm.spring_practice.domain.exceptions.NotFoundException;
import es.upm.spring_practice.domain.models.shop.Article;
import es.upm.spring_practice.domain.persistence_ports.shop.ArticlePersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.stream.Stream;

@Repository("articlePersistence")
public class ArticlePersistenceJpa implements ArticlePersistence {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticlePersistenceJpa(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Article read(String barcode) {
        return this.articleRepository
                .findByBarcode(barcode)
                .orElseThrow(() -> new NotFoundException("Article barcode: " + barcode))
                .toArticle();
    }

    @Override
    public boolean existBarcode(String barcode) {
        return this.articleRepository
                .findByBarcode(barcode)
                .isPresent();
    }

    @Override
    public Stream<Article> findByProviderAndPriceGreaterThan(String provider, BigDecimal price) {
        return this.articleRepository.findByProviderAndPriceGreaterThan(provider, price).stream()
                .map(ArticleEntity::toArticle);
    }

    @Override
    public Stream<Article> readAll() {
        return this.articleRepository
                .findAll().stream()
                .map(ArticleEntity::toArticle);
    }

    @Override
    public Article create(Article article) {
        return this.articleRepository
                .save(new ArticleEntity(article))
                .toArticle();
    }

    @Override
    public Article update(String barcode, Article article) {
        ArticleEntity articleEntity = this.articleRepository
                .findByBarcode(article.getBarcode())
                .orElseThrow(() -> new NotFoundException("Article barcode: " + article.getBarcode()));
        articleEntity.fromArticle(article);
        return this.articleRepository
                .save(articleEntity)
                .toArticle();
    }

}
