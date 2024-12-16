package bdwas.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Setter
@Getter
public class Order {
    @Id
    private String id;

    private Address deliveryAddress;

    private Account orderingAccount;

    private Account courierAccount;

    private List<Product> orderProducts;

    private String status;
}