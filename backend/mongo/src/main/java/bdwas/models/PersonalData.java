package bdwas.models;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalData {

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private Address address;

    private Gender gender;

    private LocalDate birthDate;

    @Builder
    public PersonalData(String firstName, String lastName, String phoneNumber, Address address, Gender gender, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
        this.birthDate = birthDate;
    }
}