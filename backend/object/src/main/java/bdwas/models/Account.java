package bdwas.models;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Account extends BaseEntity<Long> {

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Embedded
    private PersonalData personalData;

    @Builder
    public Account(String email, String password, Role role, PersonalData personalData) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.personalData = personalData;
    }
}