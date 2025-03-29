package infra.repository;

import infra.dto.BaseDto;
import infra.model.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseCrudRepository
        <ID extends Serializable,E extends BaseEntity<ID,E,D>,D extends BaseDto<ID,E,D>>
        extends JpaRepository<E,ID> {
    @Override
    <S extends E> S save(S entity);

    @Override
    void delete(E entity);

    @Override
    List<E> findAll();

    @Override
    void deleteById(ID id);

    @Override
    Optional<E> findById(ID id);
}
