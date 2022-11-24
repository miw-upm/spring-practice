package es.upm.spring_practice.adapters.rest.shop;

import es.upm.spring_practice.adapters.rest.RestClientTestService;
import es.upm.spring_practice.adapters.rest.RestTestConfig;
import es.upm.spring_practice.domain.models.shop.Article;
import es.upm.spring_practice.domain.models.shop.ArticleItem;
import es.upm.spring_practice.domain.models.shop.ShoppingCart;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RestTestConfig
class ShoppingCartEntityResourceIT {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private RestClientTestService restClientTestService;

    @Test
    void testUpdate() {
        Article article1 = new Article("84002", "art 002", new BigDecimal("0.27"), "prov 2");
        Article article2 = new Article("84003", "art 003", new BigDecimal("12.13"), "prov 3");
        List<ArticleItem> articleItemArray = Arrays.asList(
                new ArticleItem(article1, 2, BigDecimal.ONE),
                new ArticleItem(article2, 3, BigDecimal.TEN)
        );
        this.restClientTestService.loginAdmin(webTestClient)
                .put()
                .uri(ShoppingCartResource.SHOPPING_CARTS + ShoppingCartResource.ID_ID + ShoppingCartResource.ARTICLE_ITEMS, "0")
                .body(BodyInserters.fromValue(articleItemArray))
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testFindByPriceGreaterThan() {
        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(uriBuilder ->
                        uriBuilder.path(ShoppingCartResource.SHOPPING_CARTS + ShoppingCartResource.SEARCH)
                                .queryParam("q", "price:5.0")
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ShoppingCart.class)
                .value(shoppingCartReferenceDtoList -> shoppingCartReferenceDtoList.get(0).getUser(), equalTo("user2"))
                .value(shoppingCartReferenceDtoList -> assertTrue(shoppingCartReferenceDtoList.size() > 0));
    }
}
