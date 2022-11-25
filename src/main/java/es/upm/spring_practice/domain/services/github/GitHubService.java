package es.upm.spring_practice.domain.services.github;

import es.upm.spring_practice.domain.models.github.Issue;
import es.upm.spring_practice.domain.ports.github.GitHubMicroservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GitHubService {
    private final GitHubMicroservice gitHubMicroservice;

    @Autowired
    public GitHubService(GitHubMicroservice gitHubMicroservice) {
        this.gitHubMicroservice = gitHubMicroservice;
    }

    public Mono<Issue> createIssue(Issue issue) {
        return this.gitHubMicroservice.createIssue(issue);
    }
}
