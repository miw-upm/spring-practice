package es.upm.spring_practice.adapters.jpa.shop.daos;


import es.upm.spring_practice.adapters.jpa.shop.entities.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Integer> {
    Optional<ArticleEntity> findByBarcode(String barcode);
}
