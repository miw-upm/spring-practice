package es.upm.spring_practice.adapters.jpa.shop.persistence;

import es.upm.spring_practice.TestConfig;
import es.upm.spring_practice.domain.models.shop.Article;
import es.upm.spring_practice.domain.models.shop.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestConfig
class TagPersistenceJpaIT {

    @Autowired
    private TagPersistenceJpa tagPersistence;

    @Test
    void testReadByName() {
        Tag tag = this.tagPersistence.readByName("tag2");
        assertEquals("tag 2", tag.getDescription());
        assertTrue(tag.getFavourite());
        assertEquals(2, tag.getArticles().size());
        assertTrue(tag.getArticles().stream()
                .map(Article::getBarcode)
                .collect(Collectors.toList())
                .containsAll(List.of("84001", "84004")));
    }

}
