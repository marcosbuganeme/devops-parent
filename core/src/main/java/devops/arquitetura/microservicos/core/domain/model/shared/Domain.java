package devops.arquitetura.microservicos.core.domain.model.shared;

import java.io.Serializable;
import java.util.Objects;

@FunctionalInterface
public interface Domain<T extends Serializable> extends Serializable {

    T getId();

    default boolean isNew() {
        return Objects.isNull(getId());
    }
}