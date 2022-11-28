package es.upm.spring_practice.adapters.rest;

import es.upm.spring_practice.domain.models.Role;
import es.upm.spring_practice.domain.models.User;
import es.upm.spring_practice.domain.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(UserResource.USERS)
public class UserResource {
    public static final String USERS = "/users";
    public static final String TOKEN = "/token";
    public static final String MOBILE_ID = "/{mobile}";
    public static final String SEARCH = "/search";
    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @SecurityRequirement(name = "basicAuth")
    @PreAuthorize("authenticated")
    @PostMapping(value = TOKEN)
    public Mono<TokenDto> login(@AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {
        return userService.login(principal.getUsername())
                .map(TokenDto::new)
                .map(Mono::just)
                .orElse(Mono.empty());
    }

    private Role extractRole(UsernamePasswordAuthenticationToken principal) {
        return principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(Role::of)
                .findFirst().orElse(null);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public Mono<Void> createUser(@Valid @RequestBody User creationUser, @AuthenticationPrincipal UsernamePasswordAuthenticationToken principal) {
        this.userService.createUser(creationUser, this.extractRole(principal));
        return Mono.empty();
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(MOBILE_ID)
    public Mono<User> readUser(@PathVariable String mobile) {
        return Mono.just(this.userService.readByMobileAssured(mobile));
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public Flux<User> readAll(@AuthenticationPrincipal UsernamePasswordAuthenticationToken principal) {
        return Flux.fromStream(this.userService.readAll(this.extractRole(principal))
                .map(User::ofMobileFirstName));
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(value = SEARCH)
    public Flux<User> findByMobileAndFirstNameAndFamilyNameAndEmailAndDniContainingNullSafe(
            @RequestParam(required = false) String mobile, @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String familyName, @RequestParam(required = false) String email,
            @RequestParam(required = false) String dni,
            @AuthenticationPrincipal UsernamePasswordAuthenticationToken principal) {
        return Flux.fromStream(this.userService.findByMobileAndFirstNameAndFamilyNameAndEmailAndDniContainingNullSafe(
                        mobile, firstName, familyName, email, dni, this.extractRole(principal))
                .map(User::ofMobileFirstName));
    }

}
