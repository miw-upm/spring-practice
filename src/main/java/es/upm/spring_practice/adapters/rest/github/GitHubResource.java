package es.upm.spring_practice.adapters.rest.github;

import es.upm.spring_practice.adapters.rest.Rest;
import es.upm.spring_practice.domain.models.github.Issue;
import es.upm.spring_practice.domain.services.github.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Rest
@RequestMapping(GitHubResource.GITHUB)
public class GitHubResource {
    public static final String GITHUB = "/github";
    public static final String ISSUES = "/issues";

    private final GitHubService gitHubService;

    @Autowired
    public GitHubResource(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @PostMapping(ISSUES)
    public Mono<Issue> createIssue(@Valid @RequestBody Issue issue) {
        return this.gitHubService.createIssue(issue);
    }
}
