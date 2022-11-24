package es.upm.spring_practice.adapters.jpa;

import es.upm.spring_practice.domain.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByMobile(String mobile);

    List<UserEntity> findByRoleIn(Collection<Role> roles);

    @Query("SELECT u FROM UserEntity u WHERE " +
            "(?1 is null or u.mobile like concat('%',?1,'%')) and " +
            "(?2 is null or lower(u.firstName) like lower(concat('%',?2,'%'))) and" +
            "(?3 is null or lower(u.familyName) like lower(concat('%',?3,'%'))) and" +
            "(?4 is null or lower(u.email) like lower(concat('%',?4,'%'))) and" +
            "(?5 is null or lower(u.dni) like lower(concat('%',?5,'%'))) and" +
            "(u.role in ?6)")
    List<UserEntity> findByMobileAndFirstNameAndFamilyNameAndEmailAndDniContainingNullSafe(
            String mobile, String firstName, String familyName, String email, String dni, Collection<Role> roles);
}
