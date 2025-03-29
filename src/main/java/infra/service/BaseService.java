package infra.service;

import infra.dto.BaseDto;
import infra.model.BaseEntity;
import infra.repository.BaseCrudRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class BaseService
        <ID extends Serializable, E extends BaseEntity<ID, E, D>, D extends BaseDto<ID, E, D>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseService.class);

    public abstract BaseCrudRepository<ID, E, D> getRepository();

    public abstract Class<D> getDtoClazz();

    public abstract Class<E> getEntityClazz();

    @Transactional
    public D save(D dto) {
        try {
            E entity = dto.convert(getEntityClazz());
            E saveEntity = getRepository().save(entity);
            return saveEntity.convert(getDtoClazz());
        } catch (Exception e) {
            LOGGER.error("Error during save operation: {}", e.getMessage(), e);
            throw new ServiceException("Failed to save entity", e);
        }
    }

    @Transactional
    public List<D> findAll() {
        return getRepository().findAll().stream()
                .map(e -> e.convert(getDtoClazz()))
                .collect(Collectors.toList());
    }

    public D findById(ID id) {
        return
                getRepository().findById(id).map(e -> e.convert(getDtoClazz()))
                        .orElseThrow(() -> new EntityNotFoundException("Entity not found with id : " + id));
    }

    @Transactional
    public D update(ID id, D dto) {
        Optional<E> optionalEntity = getRepository().findById(id);
        if (optionalEntity.isEmpty()) {
            throw new EntityNotFoundException("Entity not found with id : \"+ id");
        }
        try {
            E updateEntity = dto.convert(getEntityClazz());
            updateEntity.setId(id);
            E saveEntity = getRepository().save(updateEntity);
            return saveEntity.convert(getDtoClazz());
        } catch (Exception e) {
            LOGGER.error("Error during update operation: {}", e.getMessage(), e);
            throw new ServiceException("Failed to update entity", e);
        }
    }
    @Transactional
    public void delete(ID id) {
        if (!getRepository().existsById(id)) {
            throw new EntityNotFoundException("Entity not found with ID: " + id);
        }
        getRepository().deleteById(id);
    }

    @Transactional
    public void deleteById(ID id) {
        if (!getRepository().existsById(id)) {
            throw new EntityNotFoundException("Entity not found with id : " + id);
        }
        getRepository().deleteById(id);
    }
}
