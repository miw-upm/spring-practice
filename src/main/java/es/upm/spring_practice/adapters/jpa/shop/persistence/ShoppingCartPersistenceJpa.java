package es.upm.spring_practice.adapters.jpa.shop.persistence;

import es.upm.spring_practice.adapters.jpa.shop.daos.ShoppingCartRepository;
import es.upm.spring_practice.adapters.jpa.shop.entities.ShoppingCartEntity;
import es.upm.spring_practice.domain.exceptions.NotFoundException;
import es.upm.spring_practice.domain.models.shop.ShoppingCart;
import es.upm.spring_practice.domain.persistence_ports.shop.ShoppingCartPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository("shoppingCartPersistence")
public class ShoppingCartPersistenceJpa implements ShoppingCartPersistence {

    private final ShoppingCartRepository shoppingCartRepository;

    @Autowired
    public ShoppingCartPersistenceJpa(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }


    @Override
    public Stream<ShoppingCart> readAll() {
        return this.shoppingCartRepository.findAll().stream()
                .map(ShoppingCartEntity::toShoppingCart);
    }

    @Override
    public ShoppingCart readById(Integer id) {
        return this.shoppingCartRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("ShoppingCart id:" + id))
                .toShoppingCart();
    }

}
