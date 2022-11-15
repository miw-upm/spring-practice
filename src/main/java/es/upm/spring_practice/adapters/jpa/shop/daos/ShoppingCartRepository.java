package es.upm.spring_practice.adapters.jpa.shop.daos;


import es.upm.spring_practice.adapters.jpa.shop.entities.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Integer> {
}
