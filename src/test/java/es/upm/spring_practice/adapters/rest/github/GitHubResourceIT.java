package es.upm.spring_practice.adapters.rest.github;

import es.upm.spring_practice.adapters.rest.RestClientTestService;
import es.upm.spring_practice.adapters.rest.RestTestConfig;
import es.upm.spring_practice.domain.models.github.Issue;
import es.upm.spring_practice.domain.models.github.Label;
import es.upm.spring_practice.domain.services.github.GitHubService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.mockito.ArgumentMatchers.any;

@RestTestConfig
public class GitHubResourceIT {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private RestClientTestService restClientTestService;
    @MockBean
    private GitHubService gitHubService;

    @Test
    void testCreate() {
        BDDMockito.given(this.gitHubService.createIssue(any(Issue.class)))
                .willAnswer(arguments -> Mono.just(arguments.getArgument(0)));
        Issue issue = Issue.builder().title("Test").body("test")
                .label(new Label("L1")).label(new Label("l2")).build();
        this.restClientTestService.loginAdmin(webTestClient)
                .mutate()
                .responseTimeout(Duration.ofMillis(50000)).build()
                .post()
                .uri(GitHubResource.GITHUB + GitHubResource.ISSUES)
                .body(Mono.just(issue), Issue.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Issue.class)
                .value(Assertions::assertNotNull);


    }
}
