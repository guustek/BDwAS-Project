package bdwas.models;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "accounts")
public class Account {

    @Id
    private ObjectId id;

    private String email;

    private String password;

    private Role role;

    private PersonalData personalData;

    @Builder
    public Account(String email, String password, Role role, PersonalData personalData) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.personalData = personalData;
    }
}