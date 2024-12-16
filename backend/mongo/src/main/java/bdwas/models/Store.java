package bdwas.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Setter
@Getter
public class Store {
    @Id
    private String id;

    private Address address;

    private List<Product> storeProducts;

    private Account manager;
}