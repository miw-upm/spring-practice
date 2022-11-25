package es.upm.spring_practice.adapters.jpa;

import es.upm.spring_practice.domain.models.Role;
import es.upm.spring_practice.domain.models.User;
import es.upm.spring_practice.domain.ports.UserPersistence;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository("userPersistence")
public class UserPersistenceJpa implements UserPersistence {

    private final UserRepository userRepository;

    public UserPersistenceJpa(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void create(User user) {
        this.userRepository.save(new UserEntity(user));
    }

    @Override
    public Optional<User> findByMobile(String mobile) {
        return this.userRepository.findByMobile(mobile)
                .map(UserEntity::toUser);
    }

    @Override
    public Stream<User> findByRoleIn(List<Role> roles) {
        return this.userRepository.findByRoleIn(roles).stream()
                .map(UserEntity::toUser);
    }

    @Override
    public Stream<User> findByMobileAndFirstNameAndFamilyNameAndEmailAndDniContainingNullSafe(String mobile, String firstName, String familyName, String email, String dni, List<Role> roles) {
        return this.userRepository.findByMobileAndFirstNameAndFamilyNameAndEmailAndDniContainingNullSafe
                        (mobile, firstName, familyName, email, dni, roles).stream()
                .map(UserEntity::toUser);
    }

}
