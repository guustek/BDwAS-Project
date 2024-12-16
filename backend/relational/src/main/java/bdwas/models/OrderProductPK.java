package bdwas.models;

import java.io.Serializable;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

@Setter
@Getter
public class OrderProductPK implements Serializable {

    private Order order;

    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrderProductPK entity = (OrderProductPK) o;
        return Objects.equals(this.product, entity.product) &&
                Objects.equals(this.order, entity.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, order);
    }

}