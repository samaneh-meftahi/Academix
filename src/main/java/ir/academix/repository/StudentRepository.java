package ir.academix.repository;

import infra.repository.BaseCrudRepository;
import ir.academix.dto.StudentDto;
import ir.academix.model.ProfessorEntity;
import ir.academix.model.StudentEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends
        BaseCrudRepository<Long, StudentEntity, StudentDto> {
    List<StudentEntity> findByName(String name);
    List<StudentEntity> findByLastname(String lastName);
    List<StudentEntity> findByNameAndLastname(String name, String lastName);
    List<StudentEntity> findByCourses_Id(Long courseId);

    List<StudentEntity> findByProfessors(ProfessorEntity professor);

}
