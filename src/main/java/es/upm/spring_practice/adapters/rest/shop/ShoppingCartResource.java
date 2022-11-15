package es.upm.spring_practice.adapters.rest.shop;

import es.upm.spring_practice.adapters.rest.LexicalAnalyzer;
import es.upm.spring_practice.domain.models.shop.ShoppingCart;
import es.upm.spring_practice.domain.services.shop.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.stream.Stream;

@RestController
@RequestMapping(ShoppingCartResource.SHOPPING_CARTS)
public class ShoppingCartResource {
    static final String SHOPPING_CARTS = "/shop/shopping-carts";

    static final String ID_ID = "/{id}";
    static final String ARTICLE_ITEMS = "/article-items";
    static final String SEARCH = "/search";

    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartResource(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping(SEARCH)
    public Stream<ShoppingCart> findByPriceGreaterThan(@RequestParam String q) {
        BigDecimal price = new LexicalAnalyzer().extractWithAssure(q, "price", BigDecimal::new);
        return this.shoppingCartService.findByPriceGreaterThan(price)
                .map(ShoppingCart::ofIdUser);
    }
}
