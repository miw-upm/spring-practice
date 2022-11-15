package es.upm.spring_practice.adapters.jpa.shop.entities;

import es.upm.spring_practice.domain.models.shop.ArticleItem;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity
public class ArticleItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ManyToOne //@JoinColumn
    private ArticleEntity articleEntity;
    private Integer amount;
    private BigDecimal discount;

    public ArticleItemEntity(ArticleEntity articleEntity, Integer amount, BigDecimal discount) {
        this.articleEntity = articleEntity;
        this.amount = amount;
        this.discount = discount;
    }

    public ArticleItem toArticleItem() {
        return new ArticleItem(this.articleEntity.toArticle(), this.amount, this.discount);
    }

}
