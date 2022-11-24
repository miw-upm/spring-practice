package es.upm.spring_practice.configuration;

import es.upm.spring_practice.domain.models.Role;
import es.upm.spring_practice.domain.services.JwtService;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import reactor.core.publisher.Mono;

import java.util.List;

public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtService jwtService;

    public JwtAuthenticationManager(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();
        GrantedAuthority authority = new SimpleGrantedAuthority(Role.PREFIX + jwtService.role(token));
        return Mono.just(new UsernamePasswordAuthenticationToken(
                jwtService.user(token), authentication.getCredentials(), List.of(authority)));
    }
}
