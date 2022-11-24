package es.upm.spring_practice.domain.persistence_ports;

import es.upm.spring_practice.domain.models.Role;
import es.upm.spring_practice.domain.models.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface UserPersistence {
    void create(User user);

    Optional<User> findByMobile(String mobile);

    Stream<User> findByRoleIn(List<Role> roles);

    Stream<User> findByMobileAndFirstNameAndFamilyNameAndEmailAndDniContainingNullSafe(
            String mobile, String firstName, String familyName, String email, String dni, List<Role> roles);
}
