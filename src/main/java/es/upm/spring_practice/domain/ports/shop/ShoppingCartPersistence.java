package es.upm.spring_practice.domain.ports.shop;

import es.upm.spring_practice.domain.models.shop.ShoppingCart;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface ShoppingCartPersistence {
    Stream<ShoppingCart> readAll();

    ShoppingCart readById(Integer id);
}
