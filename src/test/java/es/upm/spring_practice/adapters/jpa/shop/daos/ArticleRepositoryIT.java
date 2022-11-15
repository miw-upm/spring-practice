package es.upm.spring_practice.adapters.jpa.shop.daos;

import es.upm.spring_practice.TestConfig;
import es.upm.spring_practice.adapters.jpa.shop.entities.ArticleEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@TestConfig
class ArticleRepositoryIT {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    void testFindByBarcode() {
        assertTrue(this.articleRepository.findByBarcode("84003").isPresent());
        ArticleEntity article = this.articleRepository.findByBarcode("84003").get();
        assertEquals("art 003", article.getSummary());
        assertEquals(0, new BigDecimal("12.13").compareTo(article.getPrice()));
        assertEquals("prov 3", article.getProvider());
    }

    @Test
    void testFindByProviderAndPriceGreaterThan() {
        List<ArticleEntity> articles = this.articleRepository.findByProviderAndPriceGreaterThan("prov 1", null);
        assertFalse(articles.isEmpty());
        assertTrue(articles.stream()
                .map(ArticleEntity::getBarcode)
                .collect(Collectors.toList())
                .containsAll(Arrays.asList("84001")));
    }

}
