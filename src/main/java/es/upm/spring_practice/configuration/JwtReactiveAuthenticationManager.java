package es.upm.spring_practice.configuration;

import es.upm.spring_practice.domain.models.Role;
import es.upm.spring_practice.domain.services.JwtService;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import reactor.core.publisher.Mono;

import java.util.List;

public class JwtReactiveAuthenticationManager implements ReactiveAuthenticationManager {
    private final JwtService jwtService;

    public JwtReactiveAuthenticationManager(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();
        GrantedAuthority authority = new SimpleGrantedAuthority(Role.PREFIX + jwtService.role(token));
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(jwtService.mobile(token), token, List.of(authority)));
        return Mono.just(new UsernamePasswordAuthenticationToken(jwtService.mobile(token), null, List.of(authority)));
    }
}
