package bdwas.models;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Setter
@Getter
public class PersonalData {
    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private Address address;

    private String gender;

    private LocalDate birthDate;
}