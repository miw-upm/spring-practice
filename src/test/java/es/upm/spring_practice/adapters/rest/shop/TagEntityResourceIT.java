package es.upm.spring_practice.adapters.rest.shop;

import es.upm.spring_practice.adapters.rest.RestClientTestService;
import es.upm.spring_practice.adapters.rest.RestTestConfig;
import es.upm.spring_practice.domain.models.shop.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static es.upm.spring_practice.adapters.rest.shop.TagResource.*;
import static org.junit.jupiter.api.Assertions.*;

@RestTestConfig
class TagEntityResourceIT {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private RestClientTestService restClientTestService;

    @Test
    void testRead() {
        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(TAGS + NAME_ID, "tag3")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Tag.class)
                .value(Assertions::assertNotNull)
                .value(tagData -> {
                    assertEquals("tag 3", tagData.getDescription());
                    assertEquals(1, tagData.getArticles().size());
                    assertEquals("84002", tagData.getArticles().get(0).getBarcode());
                    assertFalse(tagData.getFavourite());
                });
    }

    @Test
    void testReadNotFound() {
        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(TAGS + NAME_ID, "kk")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testDelete() {
        this.restClientTestService.loginAdmin(webTestClient)
                .delete()
                .uri(TAGS + NAME_ID, "kk")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testFindByArticlesInShoppingCarts() {
        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(TAGS + SEARCH)
                        .queryParam("q", "shopping-carts:in")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Tag.class)
                .consumeWith(entityList -> {
                    assertNotNull(entityList.getResponseBody());
                    List<String> tagList = entityList.getResponseBody().stream()
                            .map(Tag::getName)
                            .collect(Collectors.toList());
                    assertTrue(tagList.containsAll(Arrays.asList("tag1", "tag2", "tag3")));
                    assertFalse(tagList.contains("tag4"));
                });
    }

    @Test
    void testFindByArticlesInShoppingCartsBadRequest() {
        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(uriBuilder ->
                        uriBuilder.path(TAGS + SEARCH)
                                .queryParam("q", "shopping-carts:kk")
                                .build())
                .exchange()
                .expectStatus().isBadRequest();
    }
}
