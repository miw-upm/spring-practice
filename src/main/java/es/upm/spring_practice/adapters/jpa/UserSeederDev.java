package es.upm.spring_practice.adapters.jpa;

import es.upm.spring_practice.domain.models.Role;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Arrays;

@Repository // @Profile("dev")
public class UserSeederDev {
    private final DatabaseStarting databaseStarting;
    private final UserRepository userRepository;
    private final String pass;

    @Autowired
    public UserSeederDev(@Value("${miw.user.pass}") String pass, UserRepository userRepository,
                         DatabaseStarting databaseStarting) {
        this.pass = pass;
        this.userRepository = userRepository;
        this.databaseStarting = databaseStarting;
    }

    public void deleteAllAndInitialize() {
        this.userRepository.deleteAll();
        LogManager.getLogger(this.getClass()).warn("------- Users deleted All -------");
        this.databaseStarting.initialize();
    }

    public void seedDataBase() {
        String pass = new BCryptPasswordEncoder().encode(this.pass);
        UserEntity[] userEntities = {
                UserEntity.builder().mobile("666666000").firstName("adm").password(pass).dni(null).address("C/TPV, 0")
                        .email("adm@gmail.com").role(Role.ADMIN).registrationDate(LocalDateTime.now()).active(true)
                        .build(),
                UserEntity.builder().mobile("666666001").firstName("man").password(pass).dni("66666601C").address("C/TPV, 1")
                        .email("man@gmail.com").role(Role.MANAGER).registrationDate(LocalDateTime.now()).active(true)
                        .build(),
                UserEntity.builder().mobile("666666002").firstName("ope").password(pass).dni("66666602K").address("C/TPV, 2")
                        .email("ope@gmail.com").role(Role.OPERATOR).registrationDate(LocalDateTime.now()).active(true)
                        .build(),
                UserEntity.builder().mobile("666666003").firstName("c1").familyName("ac1").password(pass).dni("66666603E")
                        .address("C/TPV, 3").email("c1@gmail.com").role(Role.CUSTOMER)
                        .registrationDate(LocalDateTime.now()).active(true).build(),
                UserEntity.builder().mobile("666666004").firstName("c2").familyName("ac2").password(pass).dni("66666604T")
                        .address("C/TPV, 4").email("c2@gmail.com").role(Role.CUSTOMER)
                        .registrationDate(LocalDateTime.now()).active(true).build(),
                UserEntity.builder().mobile("666666005").firstName("c3").password(pass).role(Role.CUSTOMER)
                        .registrationDate(LocalDateTime.now()).active(true).build(),
                UserEntity.builder().mobile("66").firstName("customer").password(pass).role(Role.CUSTOMER)
                        .registrationDate(LocalDateTime.now()).active(true).build(),
        };
        this.userRepository.saveAll(Arrays.asList(userEntities));
        LogManager.getLogger(this.getClass()).warn("------- Users Initial Load -------");
    }

}
