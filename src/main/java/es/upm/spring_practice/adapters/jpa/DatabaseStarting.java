package es.upm.spring_practice.adapters.jpa;

import es.upm.spring_practice.domain.models.Role;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DatabaseStarting {
    private final UserRepository userRepository;
    private final String name;
    private final String mobile;
    private final String pass;

    @Autowired
    public DatabaseStarting(@Value("${miw.user.name}") String name, @Value("${miw.user.mobile}") String mobile,
                            @Value("${miw.user.pass}") String pass, UserRepository userRepository) {
        this.name = name;
        this.mobile = mobile;
        this.pass = pass;
        this.userRepository = userRepository;
        this.initialize();
    }

    void initialize() {
        LogManager.getLogger(this.getClass()).warn("------- Finding Admin -------------");
        if (this.userRepository.findByRoleIn(List.of(Role.ADMIN)).isEmpty()) {
            UserEntity userEntity = UserEntity.builder().mobile(mobile).firstName(name)
                    .password(new BCryptPasswordEncoder().encode(pass))
                    .role(Role.ADMIN).registrationDate(LocalDateTime.now()).active(true).build();
            this.userRepository.save(userEntity);
            LogManager.getLogger(this.getClass()).warn("------- Created Admin -------------");
        }
    }

}
