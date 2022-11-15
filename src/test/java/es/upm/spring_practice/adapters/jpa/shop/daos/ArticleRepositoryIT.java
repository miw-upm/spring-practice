package es.upm.spring_practice.adapters.jpa.shop.daos;

import es.upm.spring_practice.TestConfig;
import es.upm.spring_practice.adapters.jpa.shop.entities.ArticleEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

}
