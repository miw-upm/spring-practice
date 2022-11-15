package es.upm.spring_practice.domain.models.shop;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePriceUpdating {

    private String barcode;
    private BigDecimal price;

}
