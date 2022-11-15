package es.upm.spring_practice.domain.models.shop;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShoppingCart {
    private Integer id;
    private LocalDateTime creationDate;
    private List<ArticleItem> articleItems;
    private String user;
    private String address;

    public static ShoppingCart ofIdUser(ShoppingCart shoppingCart) {
        ShoppingCart shoppingCartDto = new ShoppingCart();
        shoppingCartDto.setId(shoppingCart.getId());
        shoppingCartDto.setUser(shoppingCart.getUser());
        return shoppingCartDto;
    }

}
