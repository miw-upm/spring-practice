package es.upm.spring_practice.adapters.jpa.shop.daos;


import es.upm.spring_practice.adapters.jpa.shop.entities.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Integer> {
    Optional<ArticleEntity> findByBarcode(String barcode);

    @Query("select a from ArticleEntity a where " +
            "(?1 is null or a.provider like concat('%',?1,'%')) and " +
            "(?2 is null or a.price>?2)")
    List<ArticleEntity> findByProviderAndPriceGreaterThan(String provider, BigDecimal price);

}
