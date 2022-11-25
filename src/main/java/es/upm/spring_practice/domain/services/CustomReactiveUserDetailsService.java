package es.upm.spring_practice.domain.services;

import es.upm.spring_practice.domain.ports.UserPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;


@Service("reactiveUserDetailsService")
public class CustomReactiveUserDetailsService implements ReactiveUserDetailsService {
    private final UserPersistence userPersistence;

    @Autowired
    public CustomReactiveUserDetailsService(UserPersistence userPersistence) {
        this.userPersistence = userPersistence;
    }

    @Override
    public Mono<UserDetails> findByUsername(final String mobile) {
        return userPersistence.findByMobile(mobile)
                .map(user -> Mono.just(org.springframework.security.core.userdetails.User.builder()
                        .username(mobile)
                        .password(user.getPassword()) // DataBase password is encoded
                        .authorities(List.of(new SimpleGrantedAuthority(user.getRole().withPrefix())))
                        .build()))
                .orElse(Mono.error(new UsernameNotFoundException("Mobile not found: '" + mobile + "'")));
    }

}
