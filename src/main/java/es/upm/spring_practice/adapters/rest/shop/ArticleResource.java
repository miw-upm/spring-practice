package es.upm.spring_practice.adapters.rest.shop;

import es.upm.spring_practice.adapters.rest.LexicalAnalyzer;
import es.upm.spring_practice.domain.models.shop.Article;
import es.upm.spring_practice.domain.models.shop.ArticlePriceUpdating;
import es.upm.spring_practice.domain.services.shop.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping(ArticleResource.ARTICLES)
public class ArticleResource {
    static final String ARTICLES = "/shop/articles";

    static final String SEARCH = "/search";

    private final ArticleService articleService;

    @Autowired
    public ArticleResource(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public Article create(@Valid @RequestBody Article article) {
        article.doDefault();
        return this.articleService.create(article);
    }

    @PatchMapping
    public void updatePrices(@RequestBody List<ArticlePriceUpdating> articlePriceUpdatingList) {
        this.articleService.updatePrices(articlePriceUpdatingList.stream());
    }

    @GetMapping(SEARCH)
    public Stream<Article> findByProviderAndPriceGreaterThan(@RequestParam String q) { // q=provider:prov1;price:1.3
        String provider = new LexicalAnalyzer().extractWithAssure(q, "provider");
        BigDecimal price = new LexicalAnalyzer().extractWithAssure(q, "price", BigDecimal::new);
        return this.articleService.findByProviderAndPriceGreaterThan(provider, price);
    }

}
