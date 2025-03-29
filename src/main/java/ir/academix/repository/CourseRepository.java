package ir.academix.repository;

import infra.repository.BaseCrudRepository;
import ir.academix.dto.CourseDto;
import ir.academix.model.CourseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends
        BaseCrudRepository<Long, CourseEntity, CourseDto> {
    List<CourseEntity> findByTitle(String title);

    //   List<CourseEntity> findCoursesByProfessorId(Long professorId);

    //List<CourseEntity> findCoursesByProfessor_Id(Long professorId);

}
