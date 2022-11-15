package es.upm.spring_practice.adapters.jpa.shop.entities;

import es.upm.spring_practice.domain.models.shop.ArticleItem;
import es.upm.spring_practice.domain.models.shop.ShoppingCart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ShoppingCartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private LocalDateTime creationDate;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ArticleItemEntity> articleItemEntities;
    private String user;
    private String address;


    public ShoppingCartEntity(List<ArticleItemEntity> articleItemEntities, String user, String address) {
        this.creationDate = LocalDateTime.now();
        this.articleItemEntities = articleItemEntities;
        this.user = user;
        this.address = address;
    }

    public ShoppingCart toShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(this, shoppingCart, "articleItemEntities");
        List<ArticleItem> articleItems = this.articleItemEntities.stream()
                .map(ArticleItemEntity::toArticleItem)
                .collect(Collectors.toList());
        shoppingCart.setArticleItems(articleItems);
        return shoppingCart;
    }
}
