package es.upm.spring_practice.adapters.jpa.shop.daos;

import es.upm.spring_practice.TestConfig;
import es.upm.spring_practice.adapters.jpa.shop.entities.ArticleEntity;
import es.upm.spring_practice.adapters.jpa.shop.entities.TagEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestConfig
class TagRepositoryIT {

    @Autowired
    private TagRepository tagRepository;

    @Test
    void testCreateAndRead() {
        assertTrue(this.tagRepository.findByName("tag2").isPresent());
        TagEntity tag = this.tagRepository.findByName("tag2").get();
        assertEquals("tag2", tag.getName());
        assertEquals("tag 2", tag.getDescription());
        assertTrue(tag.getArticleEntities().stream()
                .map(ArticleEntity::getBarcode)
                .collect(Collectors.toList())
                .containsAll(Arrays.asList("84001", "84004")));
        assertTrue(tag.getFavourite());

    }
}
