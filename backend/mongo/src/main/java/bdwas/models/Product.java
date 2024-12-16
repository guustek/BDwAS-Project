package bdwas.models;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Setter
@Getter
public class Product {
    @Id
    private String id;

    private String name;

    private BigDecimal price;

    private Category category;

    private Discount discount;
}