package ir.academix.service;

import com.fasterxml.jackson.databind.ser.Serializers;
import infra.repository.BaseCrudRepository;
import infra.service.BaseService;
import ir.academix.dto.CourseDto;
import ir.academix.dto.ProfessorDto;
import ir.academix.dto.StudentDto;
import ir.academix.exception.CourseNotFoundException;
import ir.academix.exception.StudentNotFoundException;
import ir.academix.model.CourseEntity;
import ir.academix.model.StudentEntity;
import ir.academix.repository.CourseRepository;
import ir.academix.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class StudentService extends BaseService<Long, StudentEntity, StudentDto> {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private StudentRepository repository;
    private CourseRepository courseRepository;

    @Override
    public BaseCrudRepository<Long, StudentEntity, StudentDto> getRepository() {
        return repository;
    }

    @Override
    public Class<StudentDto> getDtoClazz() {
        return StudentDto.class;
    }

    @Override
    public Class<StudentEntity> getEntityClazz() {
        return StudentEntity.class;
    }

    public StudentDto findById(Long id) {
        LOGGER.info("Fetching student by ID: {}", id);
        return super.findById(id);
    }

    public List<StudentDto> findAll() {
        LOGGER.info("Fetching all students.");
        return super.findAll();
    }

    public StudentDto saveOrUpdate(StudentDto dto) {
        validate(dto);
        LOGGER.info("Saving or updating student: {}", dto);
        return super.save(dto);
    }

    public void delete(Long id) {
        LOGGER.info("Deleting student with ID: {}", id);
        super.delete(id);
    }

    public StudentDto update(Long id, StudentDto dto) {
        validate(dto);
        LOGGER.info("Updating student with ID: {}", id);
        return super.update(id, dto);
    }

    public List<StudentDto> findByName(String name) {
        LOGGER.info("Searching students by name: {}", name);
        List<StudentEntity> students = repository.findByName(name);
        return students.stream()
                .map(student -> student.convert(getDtoClazz()))
                .collect(Collectors.toList());
    }

    public List<StudentDto> findByLastName(String lastName) {
        LOGGER.info("Searching students by last name: {}", lastName);
        List<StudentEntity> students = repository.findByLastname(lastName);
        return students.stream()
                .map(student -> student.convert(getDtoClazz()))
                .collect(Collectors.toList());
    }

    public List<StudentDto> findByNameAndLastName(String name, String lastName) {
        LOGGER.info("Searching students by name and last name: {} {}", name, lastName);
        List<StudentEntity> students = repository.findByNameAndLastname(name, lastName);
        return students.stream()
                .map(student -> student.convert(getDtoClazz()))
                .collect(Collectors.toList());
    }

    public void enrollInCourse(Long studentId, Long courseId) {
        Optional<StudentEntity> optionalStudent = repository.findById(studentId);
        Optional<CourseEntity> optionalCourse = courseRepository.findById(courseId);

        if (optionalStudent.isEmpty()) {
            throw new StudentNotFoundException(studentId);
        }
        if (optionalCourse.isEmpty()) {
            throw new CourseNotFoundException(courseId);
        }

        StudentEntity student = optionalStudent.get();
        CourseEntity course = optionalCourse.get();

        if (student.getCourses().contains(course)) {
            throw new IllegalArgumentException("Student is already enrolled in this course.");
        }

        student.getCourses().add(course);
        course.getStudents().add(student);

        repository.save(student);
    }
    public void removalFromCourse(Long studentId, Long courseId) {
        Optional<StudentEntity> optionalStudent = repository.findById(studentId);
        Optional<CourseEntity> optionalCourse = courseRepository.findById(courseId);

        if (optionalStudent.isEmpty()) {
            throw new StudentNotFoundException(studentId);
        }
        if (optionalCourse.isEmpty()) {
            throw new CourseNotFoundException(courseId);
        }

        StudentEntity student = optionalStudent.get();
        CourseEntity course = optionalCourse.get();

        if (!student.getCourses().contains(course)) {
            throw new IllegalArgumentException("Student is not enrolled in this course.");
        }

        student.getCourses().remove(course);
        course.getStudents().remove(student);

        repository.save(student);
    }
    public List<CourseDto> getEnrolledCourses(Long studentId) {
        Optional<StudentEntity> optionalStudent = repository.findById(studentId);

        if (optionalStudent.isEmpty()) {
            throw new StudentNotFoundException(studentId);
        }

        StudentEntity student = optionalStudent.get();
        return student.getCourses().stream()
                .map(course -> course.convert(CourseDto.class))
                .collect(Collectors.toList());
    }
    public List<ProfessorDto> getStudentProfessors(Long studentId) {
        Optional<StudentEntity> optionalStudent = repository.findById(studentId);

        if (optionalStudent.isEmpty()) {
            throw new StudentNotFoundException(studentId);
        }

        StudentEntity student = optionalStudent.get();
        return student.getProfessors().stream()
                .map(professor -> professor.convert(ProfessorDto.class))
                .collect(Collectors.toList());
    }

    public double calculateAverageGrade(Long studentId) {
        LOGGER.info("Calculating average grade for student with ID: {}", studentId);
        Optional<StudentEntity> optionalStudent = repository.findById(studentId);
        if (optionalStudent.isEmpty()) {
            throw new StudentNotFoundException(studentId);
        }

        StudentEntity student = optionalStudent.get();
        List<Double> grades = student.getGrades();

        if (grades.isEmpty()) {
            return 0.0;
        }

        return grades.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    private void validate(StudentDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Student DTO cannot be null");
        }
        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (dto.getLastname() == null || dto.getLastname().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }
    }
}
