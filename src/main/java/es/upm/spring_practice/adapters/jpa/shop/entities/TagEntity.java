package es.upm.spring_practice.adapters.jpa.shop.entities;

import es.upm.spring_practice.domain.models.shop.Article;
import es.upm.spring_practice.domain.models.shop.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String name;
    private String description;
    @ManyToMany(fetch = FetchType.EAGER) //@JoinColumn
    private List<ArticleEntity> articleEntities;
    private Boolean favourite;

    public TagEntity(String name, String description, List<ArticleEntity> articleEntities, Boolean favourite) {
        this.name = name;
        this.description = description;
        this.articleEntities = articleEntities;
        this.favourite = favourite;
    }

    public Tag toTag() {
        List<Article> articles = this.articleEntities.stream()
                .map(ArticleEntity::toArticle)
                .collect(Collectors.toList());
        return new Tag(name, description, articles, favourite);
    }

}
