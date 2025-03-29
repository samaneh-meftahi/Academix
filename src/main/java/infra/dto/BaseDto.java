package infra.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import infra.model.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public abstract class BaseDto
        <ID extends Serializable, E extends BaseEntity<ID, E, D>, D extends BaseDto<ID, E, D>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseDto.class);
    @JsonIgnore
    private ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public E convert(Class<E> clazz) {
        E entity = null;
        try {
             entity = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            LOGGER.error("Error in DTO to Entity conversion: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to convert DTO to Entity", e);
        }
        Objects.requireNonNull(entity).setId(this.getId());
        return entity;
    }
}

