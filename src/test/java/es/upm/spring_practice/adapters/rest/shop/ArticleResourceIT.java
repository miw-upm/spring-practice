package es.upm.spring_practice.adapters.rest.shop;

import es.upm.spring_practice.adapters.rest.RestClientTestService;
import es.upm.spring_practice.adapters.rest.RestTestConfig;
import es.upm.spring_practice.domain.models.shop.Article;
import es.upm.spring_practice.domain.models.shop.ArticlePriceUpdating;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RestTestConfig
class ArticleResourceIT {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private RestClientTestService restClientTestService;

    @Test
    void testCreate() {
        Article article =
                new Article("666004", "art rest", new BigDecimal("3.00"), null);
        this.restClientTestService.loginAdmin(webTestClient)
                .post()
                .uri(ArticleResource.ARTICLES)
                .body(BodyInserters.fromValue(article))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Article.class)
                .value(Assertions::assertNotNull);
    }

    @Test
    void testCreateUnauthorized() {
        Article article =
                new Article("666005", "art rest", new BigDecimal("3.00"), null);
        this.webTestClient
                .post()
                .uri(ArticleResource.ARTICLES)
                .body(BodyInserters.fromValue(article))
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void testCreateCustomerUnauthorized() {
        Article article =
                new Article("666005", "art rest", new BigDecimal("3.00"), null);
        this.restClientTestService.loginCustomer(webTestClient)
                .post()
                .uri(ArticleResource.ARTICLES)
                .body(BodyInserters.fromValue(article))
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    void testCreateConflict() {
        Article article =
                new Article("84001", "repeated", new BigDecimal("3.00"), null);
        this.restClientTestService.loginAdmin(webTestClient)
                .post()
                .uri(ArticleResource.ARTICLES)
                .body(BodyInserters.fromValue(article))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void testUpdatePrices() {
        List<ArticlePriceUpdating> articlePriceUpdatingList = Arrays.asList(
                new ArticlePriceUpdating("84001", new BigDecimal("1.23")),
                new ArticlePriceUpdating("84002", new BigDecimal("0.27"))
        );
        this.restClientTestService.loginAdmin(webTestClient)
                .patch()
                .uri(ArticleResource.ARTICLES)
                .body(BodyInserters.fromValue(articlePriceUpdatingList))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testUpdatePricesArticleNotFound() {
        List<ArticlePriceUpdating> articlePriceUpdatingList = List.of(
                new ArticlePriceUpdating("84001", new BigDecimal("1.23")),
                new ArticlePriceUpdating("0", BigDecimal.ONE)
        );
        this.restClientTestService.loginAdmin(webTestClient)
                .patch()
                .uri(ArticleResource.ARTICLES)
                .body(BodyInserters.fromValue(articlePriceUpdatingList))
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testSearchByProviderAndPriceGreaterThan() {
        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(uriBuilder ->
                        uriBuilder.path(ArticleResource.ARTICLES + ArticleResource.SEARCH)
                                .queryParam("provider", "prov 1")
                                .queryParam("price", 1.02)
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Article.class)
                .value(articles -> assertTrue(articles.size() > 0))
                .value(articles -> assertEquals("prov 1", articles.get(0).getProvider()))
                .value(articles -> assertTrue(new BigDecimal("1.02").compareTo(articles.get(0).getPrice()) < 0));
    }

    @Test
    void testSearchByProviderAndPriceGreaterThanBadRequest() {
        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(uriBuilder ->
                        uriBuilder.path(ArticleResource.ARTICLES + ArticleResource.SEARCH)
                                .queryParam("kk", "prov 1")
                                .queryParam("price", 1.02)
                                .build())
                .exchange()
                .expectStatus().isBadRequest();
    }
}
