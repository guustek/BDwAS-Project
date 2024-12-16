package bdwas.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Setter
@Getter
public class Address {
    @Id
    private String id;

    private String country;

    private String city;

    private String street;

    private String apartmentNumber;

    private String postalCode;

}