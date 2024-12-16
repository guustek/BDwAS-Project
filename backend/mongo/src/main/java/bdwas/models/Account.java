package bdwas.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Setter
@Getter
public class Account {
    @Id
    private String id;

    private String email;

    private String password;

    private String role;

    private PersonalData personalData;
}