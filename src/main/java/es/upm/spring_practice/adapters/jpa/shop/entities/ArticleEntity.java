package es.upm.spring_practice.adapters.jpa.shop.entities;

import es.upm.spring_practice.domain.models.shop.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;


@Builder
@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String barcode;
    private String summary;
    private BigDecimal price;
    private LocalDate registrationDate;
    private String provider;

    public ArticleEntity(Article article) {
        BeanUtils.copyProperties(article, this);
    }

    public void fromArticle(Article article) {
        BeanUtils.copyProperties(article, this);
    }

    public Article toArticle() {
        Article article = new Article();
        BeanUtils.copyProperties(this, article);
        return article;
    }

}
