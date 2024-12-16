package bdwas.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Setter
@Getter
public class Discount {
    @Id
    private String id;

    private String description;

    private BigDecimal value;

    private LocalDate startDate;

    private LocalDate endDate;
}