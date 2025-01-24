package bdwas.models;

import bdwas.benchmark.Identifiable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseEntity<I> implements Identifiable<I> {

    @Id
    protected I id;
}