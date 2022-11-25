package es.upm.spring_practice.domain.ports.github;

import es.upm.spring_practice.domain.models.github.Issue;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface GitHubMicroservice {
    Mono<Issue> createIssue(Issue issue);
}
