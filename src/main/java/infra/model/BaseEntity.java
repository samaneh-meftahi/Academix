package infra.model;

import infra.dto.BaseDto;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
@MappedSuperclass
public abstract class BaseEntity
        <ID extends Serializable, E extends BaseEntity<ID, E, D>, D extends BaseDto<ID, E, D>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseEntity.class);
    @Id
    @GeneratedValue
    @Column(name = "Id")
    private ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public D convert(Class<D> clazz) {
        D dto = null;
        try {
            dto = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        Objects.requireNonNull(dto).setId(this.getId());
        return dto;
    }
}
