package es.upm.spring_practice.domain.services.shop;

import es.upm.spring_practice.domain.models.shop.ShoppingCart;
import es.upm.spring_practice.domain.ports.shop.ArticlePersistence;
import es.upm.spring_practice.domain.ports.shop.ShoppingCartPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Stream;

@Service
public class ShoppingCartService {
    private final ShoppingCartPersistence shoppingCartPersistence;
    private final ArticlePersistence articlePersistence;

    @Autowired
    public ShoppingCartService(ShoppingCartPersistence shoppingCartPersistence, ArticlePersistence articlePersistence) {
        this.shoppingCartPersistence = shoppingCartPersistence;
        this.articlePersistence = articlePersistence;
    }

    private BigDecimal total(ShoppingCart shoppingCart) {
        return shoppingCart.getArticleItems().stream()
                .map(articleItem -> {
                    BigDecimal discount = BigDecimal.ONE.subtract(
                            articleItem.getDiscount().divide(new BigDecimal(100), 4, RoundingMode.HALF_UP)
                    );
                    BigDecimal articlePrice = this.articlePersistence.read(articleItem.getArticle().getBarcode()).getPrice();
                    return articlePrice.multiply(BigDecimal.valueOf(articleItem.getAmount())
                            .multiply(discount)
                    );
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    public Stream<ShoppingCart> findByPriceGreaterThan(BigDecimal price) {
        return this.shoppingCartPersistence.readAll()
                .filter(shoppingCart -> price.compareTo(this.total(shoppingCart)) < 0);
    }
}
