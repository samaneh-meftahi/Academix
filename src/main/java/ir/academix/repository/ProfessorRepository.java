package ir.academix.repository;

import infra.repository.BaseCrudRepository;
import ir.academix.dto.ProfessorDto;
import ir.academix.model.ProfessorEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessorRepository extends
        BaseCrudRepository<Long, ProfessorEntity, ProfessorDto>   {
    List<ProfessorEntity> findByName(String name);

    List<ProfessorEntity> findByLastname(String lastName);

    List<ProfessorEntity> findByNameAndLastname(String name, String lastName);

//    List<ProfessorEntity> findProfessorsByCourseId(Long courseId);
//
//    List<ProfessorEntity> findProfessorsByStudentId(Long studentId);
}
