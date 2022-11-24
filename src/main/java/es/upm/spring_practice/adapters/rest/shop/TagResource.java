package es.upm.spring_practice.adapters.rest.shop;

import es.upm.spring_practice.adapters.rest.LexicalAnalyzer;
import es.upm.spring_practice.adapters.rest.Rest;
import es.upm.spring_practice.domain.exceptions.BadRequestException;
import es.upm.spring_practice.domain.models.shop.Tag;
import es.upm.spring_practice.domain.services.shop.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Stream;

@Rest
@RequestMapping(TagResource.TAGS)
public class TagResource {
    static final String TAGS = "/shop/tags";

    static final String NAME_ID = "/{name}";
    static final String SEARCH = "/search";

    private final TagService tagService;

    @Autowired
    public TagResource(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(NAME_ID)
    public Mono<Tag> read(@PathVariable String name) {
        return Mono.just(Tag.ofArticleBarcode(this.tagService.read(name)));
    }

    @DeleteMapping(NAME_ID)
    public Mono<Void> delete(@PathVariable String name) {
        this.tagService.delete(name);
        return Mono.empty();
    }

    @GetMapping(SEARCH)
    public Flux<Tag> findByArticlesInShoppingCarts(@RequestParam String q) {
        if (!"in".equals(new LexicalAnalyzer().extractWithAssure(q, "shopping-carts"))) {
            throw new BadRequestException("q incorrect, expected in");
        }
        return Flux.fromStream(this.tagService.findByArticlesInShoppingCarts());
    }
}
