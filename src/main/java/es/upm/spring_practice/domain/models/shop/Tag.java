package es.upm.spring_practice.domain.models.shop;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tag {
    private String name;
    private String description;
    private List<Article> articles;
    private Boolean favourite;

    public Tag ofArticleBarcode() {
        this.articles = this.articles.stream()
                        .map(Article::ofBarcode)
                        .collect(Collectors.toList());
        return this;
    }

}
