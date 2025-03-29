package ir.academix.service;

import infra.repository.BaseCrudRepository;
import infra.service.BaseService;
import ir.academix.dto.CourseDto;
import ir.academix.dto.ProfessorDto;
import ir.academix.dto.StudentDto;
import ir.academix.exception.CourseNotFoundException;
import ir.academix.exception.ProfessorNotFoundException;
import ir.academix.exception.StudentNotFoundException;
import ir.academix.model.CourseEntity;
import ir.academix.model.ProfessorEntity;
import ir.academix.model.StudentEntity;
import ir.academix.repository.CourseRepository;
import ir.academix.repository.ProfessorRepository;
import ir.academix.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService extends BaseService<Long, CourseEntity, CourseDto> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseService.class);

    @Autowired
    private CourseRepository repository;
    private ProfessorRepository professorRepository;
    private StudentRepository studentRepository;

    @Autowired
    public CourseService(CourseRepository repository,
                         ProfessorRepository professorRepository,
                         StudentRepository studentRepository) {
        this.repository = repository;
        this.professorRepository = professorRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public BaseCrudRepository<Long, CourseEntity, CourseDto> getRepository() {
        return repository;
    }

    @Override
    public Class<CourseDto> getDtoClazz() {
        return CourseDto.class;
    }

    @Override
    public Class<CourseEntity> getEntityClazz() {
        return CourseEntity.class;
    }

    public List<CourseDto> findAll() {
        LOGGER.info("Fetching all courses.");
        return super.findAll();
    }

    public CourseDto saveOrUpdate(CourseDto dto) {
        validate(dto);
        LOGGER.info("Saving or updating course: {}", dto);
        return super.save(dto);
    }

    public void delete(Long id) {
        LOGGER.info("Deleting course with ID: {}", id);
        super.delete(id);
    }

    public CourseDto update(Long id, CourseDto dto) {
        validate(dto);
        LOGGER.info("Updating course with ID: {}", id);
        return super.update(id, dto);
    }

    public List<CourseDto> findByTitle(String title) {
        LOGGER.info("Searching courses by title: {}", title);
        List<CourseEntity> courses = repository.findByTitle(title);
        return courses.stream()
                .map(course -> course.convert(getDtoClazz()))
                .collect(Collectors.toList());
    }

    public List<StudentDto> getCourseStudents(Long courseId) {
        Optional<CourseEntity> optionalCourse = repository.findById(courseId);

        if (optionalCourse.isEmpty()) {
            throw new CourseNotFoundException(courseId);
        }

        CourseEntity course = optionalCourse.get();
        return course.getStudents().stream()
                .map(student -> student.convert(StudentDto.class))
                .collect(Collectors.toList());
    }

    public List<ProfessorDto> getCourseProfessors(Long courseId) {
        Optional<CourseEntity> optionalCourse = repository.findById(courseId);

        if (optionalCourse.isEmpty()) {
            throw new CourseNotFoundException(courseId);
        }

        CourseEntity course = optionalCourse.get();
        return course.getProfessors().stream()
                .map(professor -> professor.convert(ProfessorDto.class))
                .collect(Collectors.toList());
    }

    public void enrollStudent(Long courseId, Long studentId) {
        Optional<CourseEntity> optionalCourse = repository.findById(courseId);
        Optional<StudentEntity> optionalStudent = studentRepository.findById(studentId);

        if (optionalCourse.isEmpty()) {
            throw new CourseNotFoundException(courseId);
        }
        if (optionalStudent.isEmpty()) {
            throw new StudentNotFoundException(studentId);
        }

        CourseEntity course = optionalCourse.get();
        StudentEntity student = optionalStudent.get();

        if (course.getStudents().contains(student)) {
            throw new IllegalArgumentException("Student is already enrolled in this course.");
        }

        course.getStudents().add(student);
        student.getCourses().add(course);

        repository.save(course);
    }

    public void enEnrollStudent(Long courseId, Long studentId) {
        Optional<CourseEntity> optionalCourse = repository.findById(courseId);
        Optional<StudentEntity> optionalStudent = studentRepository.findById(studentId);

        if (optionalCourse.isEmpty()) {
            throw new CourseNotFoundException(courseId);
        }
        if (optionalStudent.isEmpty()) {
            throw new StudentNotFoundException(studentId);
        }

        CourseEntity course = optionalCourse.get();
        StudentEntity student = optionalStudent.get();

        if (!course.getStudents().contains(student)) {
            throw new IllegalArgumentException("Student is not enrolled in this course.");
        }

        course.getStudents().remove(student);
        student.getCourses().remove(course);

        repository.save(course);
    }

    public void assignProfessor(Long courseId, Long professorId) {
        Optional<CourseEntity> optionalCourse = repository.findById(courseId);
        Optional<ProfessorEntity> optionalProfessor = professorRepository.findById(professorId);

        if (optionalCourse.isEmpty()) {
            throw new CourseNotFoundException(courseId);
        }
        if (optionalProfessor.isEmpty()) {
            throw new ProfessorNotFoundException(professorId);
        }

        CourseEntity course = optionalCourse.get();
        ProfessorEntity professor = optionalProfessor.get();

        if (course.getProfessors().contains(professor)) {
            throw new IllegalArgumentException("Professor is already assigned to this course.");
        }

        course.getProfessors().add(professor);
        professor.getCourses().add(course);

        repository.save(course);
    }

    public void removeProfessor(Long courseId, Long professorId) {
        Optional<CourseEntity> optionalCourse = repository.findById(courseId);
        Optional<ProfessorEntity> optionalProfessor = professorRepository.findById(professorId);

        if (optionalCourse.isEmpty()) {
            throw new CourseNotFoundException(courseId);
        }
        if (optionalProfessor.isEmpty()) {
            throw new ProfessorNotFoundException(professorId);
        }

        CourseEntity course = optionalCourse.get();
        ProfessorEntity professor = optionalProfessor.get();

        if (!course.getProfessors().contains(professor)) {
            throw new IllegalArgumentException("Professor is not assigned to this course.");
        }

        course.getProfessors().remove(professor);
        professor.getCourses().remove(course);

        repository.save(course);
    }

    private void validate(CourseDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Course DTO cannot be null");
        }
        if (dto.getTitle() == null || dto.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
    }
}
