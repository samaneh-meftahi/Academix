package ir.academix.api;

import ir.academix.dto.CourseDto;
import ir.academix.dto.ProfessorDto;
import ir.academix.dto.StudentDto;
import ir.academix.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentApi {
    private final StudentService studentService;

    @Autowired
    public StudentApi(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        return ResponseEntity.ok(studentService.findAll());
    }

    @PostMapping
    public ResponseEntity<StudentDto> saveOrUpdateStudent(@RequestBody StudentDto dto) {
        System.out.println(dto.getLastname());
        return ResponseEntity.ok(studentService.saveOrUpdate(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable Long id, @RequestBody StudentDto dto) {
        return ResponseEntity.ok(studentService.update(id, dto));
    }

    @GetMapping("/search/name/{name}")
    public ResponseEntity<List<StudentDto>> searchStudentsByName(@PathVariable String name) {
        return ResponseEntity.ok(studentService.findByName(name));
    }

    @GetMapping("/search/last-name/{lastName}")
    public ResponseEntity<List<StudentDto>> searchStudentsByLastName(@PathVariable String lastName) {
        return ResponseEntity.ok(studentService.findByLastName(lastName));
    }

    @GetMapping("/search/full-name/{name}/{lastName}")
    public ResponseEntity<List<StudentDto>> searchStudentsByNameAndLastName(@PathVariable String name, @PathVariable String lastName) {
        return ResponseEntity.ok(studentService.findByNameAndLastName(name, lastName));
    }

    @PostMapping("/{studentId}/enroll/{courseId}")
    public ResponseEntity<Void> enrollStudentInCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        studentService.enrollInCourse(studentId, courseId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{studentId}/unenroll/{courseId}")
    public ResponseEntity<Void> unenrollStudentFromCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        studentService.removalFromCourse(studentId, courseId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{studentId}/courses")
    public ResponseEntity<List<CourseDto>> getEnrolledCourses(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentService.getEnrolledCourses(studentId));
    }

    @GetMapping("/{studentId}/professors")
    public ResponseEntity<List<ProfessorDto>> getStudentProfessors(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentService.getStudentProfessors(studentId));
    }

    @GetMapping("/{studentId}/average-grade")
    public ResponseEntity<Double> calculateAverageGrade(@PathVariable Long studentId) {
        double averageGrade = studentService.calculateAverageGrade(studentId);
        return ResponseEntity.ok(averageGrade);
    }
}
