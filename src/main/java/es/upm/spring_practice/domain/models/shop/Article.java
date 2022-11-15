package es.upm.spring_practice.domain.models.shop;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Article {
    private String barcode;
    private String summary;
    private BigDecimal price;
    private LocalDate registrationDate;
    private String provider;

    public Article(String barcode, String summary, BigDecimal price, String provider) {
        this.barcode = barcode;
        this.summary = summary;
        this.price = price;
        this.provider = provider;
    }

    public static Article ofBarcode(Article article) {
        Article articleDto = new Article();
        articleDto.setBarcode(article.getBarcode());
        return articleDto;
    }

    public void doDefault() {
        if (Objects.isNull(provider)) {
            this.provider = "various";
        }
    }

}
