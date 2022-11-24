package es.upm.spring_practice.domain.services;

import es.upm.spring_practice.domain.models.Role;
import es.upm.spring_practice.domain.models.User;
import es.upm.spring_practice.domain.persistence_ports.UserPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Qualifier("miw.users")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserPersistence userPersistence;

    @Autowired
    public UserDetailsServiceImpl(UserPersistence userPersistence) {
        this.userPersistence = userPersistence;
    }

    @Override
    public UserDetails loadUserByUsername(final String mobile) {
        User user = userPersistence.findByMobile(mobile)
                .orElseThrow(() -> new UsernameNotFoundException("Mobile not found. " + mobile));
        return this.userBuilder(user.getMobile(), user.getPassword(), new Role[]{Role.AUTHENTICATED}, user.getActive());
    }

    private org.springframework.security.core.userdetails.User userBuilder(String mobile, String password, Role[] roles,
                                                                           boolean active) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.withPrefix()));
        }
        return new org.springframework.security.core.userdetails.User(mobile, password, active, true,
                true, true, authorities);
    }
}
