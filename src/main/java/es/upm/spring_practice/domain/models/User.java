package es.upm.spring_practice.domain.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.upm.spring_practice.domain.models.validations.Validations;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @NotNull
    @NotBlank
    @Pattern(regexp = Validations.NINE_DIGITS)
    private String mobile;
    @NotNull
    @NotBlank
    private String firstName;
    private String familyName;
    private String email;
    private String dni;
    private String address;
    private String password;
    private Role role;
    private Boolean active;
    private LocalDateTime registrationDate;

    public User(User user) {
        BeanUtils.copyProperties(user, this);
        this.password = "secret";
    }

    public User ofMobileFirstName() {
        return User.builder().mobile(this.mobile).firstName(this.firstName).build();
    }

    public void doDefault() {
        if (Objects.isNull(password)) {
            password = UUID.randomUUID().toString();
        }
        if (Objects.isNull(role)) {
            this.role = Role.CUSTOMER;
        }
        if (Objects.isNull(active)) {
            this.active = true;
        }
    }

}
