package es.upm.spring_practice.adapters.microservices;

import es.upm.spring_practice.domain.exceptions.BadGatewayException;
import es.upm.spring_practice.domain.models.github.Issue;
import es.upm.spring_practice.domain.ports.github.GitHubMicroservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@Service("gitHubMicroservice")
public class GitHubMicroserviceRest implements GitHubMicroservice {
    public static final String GITHUB_URI = "https://api.github.com/repos/miw-upm/spring-practice/issues";
    public static final String GITHUB_USERNAME = "miw-upm";
    private final String secret;
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public GitHubMicroserviceRest(@Value("${miw.github.secret}") String secret, WebClient.Builder webClientBuilder) {
        this.secret = secret;
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public Mono<Issue> createIssue(Issue issue) {
        return webClientBuilder.build()
                .mutate().filter(basicAuthentication(GITHUB_USERNAME, this.secret)).build()
                .post()
                .uri(GITHUB_URI)
                .body(Mono.just(issue), Issue.class)
                .retrieve()
                .bodyToMono(Issue.class)
                .onErrorMap(Exception.class, exception ->
                        new BadGatewayException("Unexpected error: " + exception.getClass() + " - " + exception.getMessage()));
    }
}
