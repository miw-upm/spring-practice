package es.upm.spring_practice.adapters.rest.shop;

import es.upm.spring_practice.adapters.rest.LexicalAnalyzer;
import es.upm.spring_practice.adapters.rest.Rest;
import es.upm.spring_practice.domain.models.shop.Article;
import es.upm.spring_practice.domain.models.shop.ArticlePriceUpdating;
import es.upm.spring_practice.domain.services.shop.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

@Rest
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
    public Mono<Article> create(@Valid @RequestBody Article article) {
        article.doDefault();
        return Mono.just(this.articleService.create(article));
    }

    @PatchMapping
    public Mono<Void> updatePrices(@RequestBody List<ArticlePriceUpdating> articlePriceUpdatingList) {
        this.articleService.updatePrices(articlePriceUpdatingList.stream());
        return Mono.empty();
    }

    @GetMapping(SEARCH)
    public Flux<Article> findByProviderAndPriceGreaterThan(@RequestParam String q) { // q=provider:prov1;price:1.3
        String provider = new LexicalAnalyzer().extractWithAssure(q, "provider");
        BigDecimal price = new LexicalAnalyzer().extractWithAssure(q, "price", BigDecimal::new);
        return Flux.fromStream(this.articleService.findByProviderAndPriceGreaterThan(provider, price));
    }

}
