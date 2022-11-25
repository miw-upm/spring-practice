package es.upm.spring_practice.adapters.rest.shop;

import es.upm.spring_practice.adapters.rest.RestClientTestService;
import es.upm.spring_practice.adapters.rest.RestTestConfig;
import es.upm.spring_practice.domain.models.shop.ShoppingCart;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RestTestConfig
class ShoppingCartResourceIT {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private RestClientTestService restClientTestService;

    @Test
    void testFindByPriceGreaterThan() {
        this.restClientTestService.loginAdmin(webTestClient)
                .get()
                .uri(uriBuilder ->
                        uriBuilder.path(ShoppingCartResource.SHOPPING_CARTS + ShoppingCartResource.SEARCH)
                                .queryParam("price", 5.0)
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ShoppingCart.class)
                .value(shoppingCartReferenceDtoList -> shoppingCartReferenceDtoList.get(0).getUser(), equalTo("user2"))
                .value(shoppingCartReferenceDtoList -> assertTrue(shoppingCartReferenceDtoList.size() > 0));
    }
}
