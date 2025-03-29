package ir.academix.service;

import infra.repository.BaseCrudRepository;
import infra.service.BaseService;
import ir.academix.dto.CourseDto;
import ir.academix.dto.ProfessorDto;
import ir.academix.dto.StudentDto;
import ir.academix.exception.CourseNotFoundException;
import ir.academix.exception.ProfessorNotFoundException;
import ir.academix.model.CourseEntity;
import ir.academix.model.ProfessorEntity;
import ir.academix.repository.CourseRepository;
import ir.academix.repository.ProfessorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class ProfessorService extends BaseService
        <Long,ProfessorEntity,ProfessorDto> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfessorService.class);

    @Autowired
    private ProfessorRepository repository;
    private CourseRepository courseRepository;

    @Override
    public BaseCrudRepository<Long, ProfessorEntity, ProfessorDto> getRepository() {
        return repository;
    }

    @Override
    public Class<ProfessorDto> getDtoClazz() {
        return ProfessorDto.class;
    }

    @Override
    public Class<ProfessorEntity> getEntityClazz() {
        return ProfessorEntity.class;
    }

    public ProfessorDto findById(Long id) {
        LOGGER.info("Fetching professor by ID: {}", id);
        return super.findById(id);
    }

    public List<ProfessorDto> findAll() {
        LOGGER.info("Fetching all professors.");
        return super.findAll();
    }

    public ProfessorDto saveOrUpdate(ProfessorDto dto) {
        validate(dto);
        LOGGER.info("Saving or updating professor: {}", dto);
        return super.save(dto);
    }

    public void delete(Long id) {
        LOGGER.info("Deleting professor with ID: {}", id);
        super.delete(id);
    }

    public ProfessorDto update(Long id, ProfessorDto dto) {
        validate(dto);
        LOGGER.info("Updating professor with ID: {}", id);
        return super.update(id, dto);
    }

    public List<ProfessorDto> findByName(String name) {
        LOGGER.info("Searching professors by name: {}", name);
        List<ProfessorEntity> professors = repository.findByName(name);
        return professors.stream()
                .map(professor -> professor.convert(getDtoClazz()))
                .collect(Collectors.toList());
    }

    public List<ProfessorDto> findByLastName(String lastName) {
        LOGGER.info("Searching professors by last name: {}", lastName);
        List<ProfessorEntity> professors = repository.findByLastname(lastName);
        return professors.stream()
                .map(professor -> professor.convert(getDtoClazz()))
                .collect(Collectors.toList());
    }

    public List<ProfessorDto> findByNameAndLastName(String name, String lastName) {
        LOGGER.info("Searching professors by name and last name: {} {}", name, lastName);
        List<ProfessorEntity> professors = repository.findByNameAndLastname(name, lastName);
        return professors.stream()
                .map(professor -> professor.convert(getDtoClazz()))
                .collect(Collectors.toList());
    }

    public void assignToCourse(Long professorId, Long courseId) {
        Optional<ProfessorEntity> optionalProfessor = repository.findById(professorId);
        Optional<CourseEntity> optionalCourse = courseRepository.findById(courseId);

        if (optionalProfessor.isEmpty()) {
            throw new ProfessorNotFoundException(professorId);
        }
        if (optionalCourse.isEmpty()) {
            throw new CourseNotFoundException(courseId);
        }

        ProfessorEntity professor = optionalProfessor.get();
        CourseEntity course = optionalCourse.get();

        if (professor.getCourses().contains(course)) {
            throw new IllegalArgumentException("Professor is already assigned to this course.");
        }

        professor.getCourses().add(course);
        course.getProfessors().add(professor);

        repository.save(professor);
    }

    public void removeFromCourse(Long professorId, Long courseId) {
        Optional<ProfessorEntity> optionalProfessor = repository.findById(professorId);
        Optional<CourseEntity> optionalCourse = courseRepository.findById(courseId);

        if (optionalProfessor.isEmpty()) {
            throw new ProfessorNotFoundException(professorId);
        }
        if (optionalCourse.isEmpty()) {
            throw new CourseNotFoundException(courseId);
        }

        ProfessorEntity professor = optionalProfessor.get();
        CourseEntity course = optionalCourse.get();

        if (!professor.getCourses().contains(course)) {
            throw new IllegalArgumentException("Professor is not assigned to this course.");
        }

        professor.getCourses().remove(course);
        course.getProfessors().remove(professor);

        repository.save(professor);
    }

    public List<CourseDto> getAssignedCourses(Long professorId) {
        Optional<ProfessorEntity> optionalProfessor = repository.findById(professorId);

        if (optionalProfessor.isEmpty()) {
            throw new ProfessorNotFoundException(professorId);
        }

        ProfessorEntity professor = optionalProfessor.get();
        return professor.getCourses().stream()
                .map(course -> course.convert(CourseDto.class))
                .collect(Collectors.toList());
    }

    public List<StudentDto> getProfessorStudents(Long professorId) {
        Optional<ProfessorEntity> optionalProfessor = repository.findById(professorId);

        if (optionalProfessor.isEmpty()) {
            throw new ProfessorNotFoundException(professorId);
        }

        ProfessorEntity professor = optionalProfessor.get();
        return professor.getStudents().stream()
                .map(student -> student.convert(StudentDto.class))
                .collect(Collectors.toList());
    }

    private void validate(ProfessorDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Professor DTO cannot be null");
        }
        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (dto.getLastname() == null || dto.getLastname().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }
    }
}
