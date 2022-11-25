package es.upm.spring_practice.domain.services;

import es.upm.spring_practice.domain.exceptions.ConflictException;
import es.upm.spring_practice.domain.exceptions.ForbiddenException;
import es.upm.spring_practice.domain.exceptions.NotFoundException;
import es.upm.spring_practice.domain.models.Role;
import es.upm.spring_practice.domain.models.User;
import es.upm.spring_practice.domain.persistence_ports.UserPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class UserService {
    private final UserPersistence userPersistence;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserPersistence userPersistence, JwtService jwtService) {
        this.userPersistence = userPersistence;
        this.jwtService = jwtService;
    }

    public Optional<String> login(String mobile) {
        return this.userPersistence.findByMobile(mobile)
                .map(user -> jwtService.createToken(user.getMobile(), user.getFirstName(), user.getRole().name()));
    }

    public void createUser(User user, Role roleClaim) {
        user.doDefault();
        if (!authorizedRoles(roleClaim).contains(user.getRole())) {
            throw new ForbiddenException("Insufficient role to create this user: " + user);
        }
        this.assertNoExistByMobile(user.getMobile());
        user.setRegistrationDate(LocalDateTime.now());
        this.userPersistence.create(user);
    }

    public Stream<User> readAll(Role roleClaim) {
        return this.userPersistence.findByRoleIn(authorizedRoles(roleClaim));
    }

    private List<Role> authorizedRoles(Role roleClaim) {
        if (Role.ADMIN.equals(roleClaim)) {
            return List.of(Role.ADMIN, Role.MANAGER, Role.OPERATOR, Role.CUSTOMER);
        } else if (Role.MANAGER.equals(roleClaim)) {
            return List.of(Role.MANAGER, Role.OPERATOR, Role.CUSTOMER);
        } else if (Role.OPERATOR.equals(roleClaim)) {
            return List.of(Role.CUSTOMER);
        } else {
            return List.of();
        }
    }

    private void assertNoExistByMobile(String mobile) {
        if (this.userPersistence.findByMobile(mobile).isPresent()) {
            throw new ConflictException("The mobile already exists: " + mobile);
        }
    }

    public Stream<User> findByMobileAndFirstNameAndFamilyNameAndEmailAndDniContainingNullSafe(
            String mobile, String firstName, String familyName, String email, String dni, Role roleClaim) {
        return this.userPersistence.findByMobileAndFirstNameAndFamilyNameAndEmailAndDniContainingNullSafe(
                mobile, firstName, familyName, email, dni, this.authorizedRoles(roleClaim));
    }

    public User readByMobileAssured(String mobile) {
        return this.userPersistence.findByMobile(mobile)
                .orElseThrow(() -> new NotFoundException("The mobile don't exist: " + mobile));
    }

}
