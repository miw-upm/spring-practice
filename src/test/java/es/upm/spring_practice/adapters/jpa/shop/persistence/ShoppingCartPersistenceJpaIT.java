package es.upm.spring_practice.adapters.jpa.shop.persistence;

import es.upm.spring_practice.TestConfig;
import es.upm.spring_practice.domain.models.shop.ShoppingCart;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestConfig
class ShoppingCartPersistenceJpaIT {

    @Autowired
    private ShoppingCartPersistenceJpa shoppingCartPersistenceJpaDb;

    @Test
    void testReadById() {
        Optional<ShoppingCart> shoppingCart = this.shoppingCartPersistenceJpaDb.readAll()
                .filter(cart -> "user1".equals(cart.getUser()))
                .findFirst();
        assertTrue(shoppingCart.isPresent());
        assertNotNull(shoppingCart.get().getId());
        assertNotNull(shoppingCart.get().getCreationDate());
    }

}
