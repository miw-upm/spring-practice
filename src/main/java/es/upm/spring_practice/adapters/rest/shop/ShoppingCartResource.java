package es.upm.spring_practice.adapters.rest.shop;

import es.upm.spring_practice.adapters.rest.Rest;
import es.upm.spring_practice.domain.models.shop.ShoppingCart;
import es.upm.spring_practice.domain.services.shop.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;

@Rest
@RequestMapping(ShoppingCartResource.SHOPPING_CARTS)
public class ShoppingCartResource {
    static final String SHOPPING_CARTS = "/shop/shopping-carts";
    static final String SEARCH = "/search";
    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartResource(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping(SEARCH)
    public Flux<ShoppingCart> findByPriceGreaterThan(@RequestParam BigDecimal price) {
        return Flux.fromStream(this.shoppingCartService.findByPriceGreaterThan(price)
                .map(ShoppingCart::ofIdUser)
        );
    }
}
