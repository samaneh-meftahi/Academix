package ir.academix.api;

import ir.academix.dto.CourseDto;
import ir.academix.dto.ProfessorDto;
import ir.academix.dto.StudentDto;
import ir.academix.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseApi {
    private final CourseService courseService;

    @Autowired
    public CourseApi(CourseService courseService) {
        this.courseService = courseService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        return ResponseEntity.ok(courseService.findAll());
    }

    @PostMapping
    public ResponseEntity<CourseDto> saveOrUpdateCourse(@RequestBody CourseDto dto) {
        return ResponseEntity.ok(courseService.saveOrUpdate(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable Long id, @RequestBody CourseDto dto) {
        return ResponseEntity.ok(courseService.update(id, dto));
    }

    @GetMapping("/search/title/{title}")
    public ResponseEntity<List<CourseDto>> searchCoursesByTitle(@PathVariable String title) {
        return ResponseEntity.ok(courseService.findByTitle(title));
    }

    @GetMapping("/{courseId}/students")
    public ResponseEntity<List<StudentDto>> getCourseStudents(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getCourseStudents(courseId));
    }

    @GetMapping("/{courseId}/professors")
    public ResponseEntity<List<ProfessorDto>> getCourseProfessors(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getCourseProfessors(courseId));
    }

    @PostMapping("/{courseId}/enroll/{studentId}")
    public ResponseEntity<Void> enrollStudentInCourse(@PathVariable Long courseId, @PathVariable Long studentId) {
        courseService.enrollStudent(courseId, studentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{courseId}/unenroll/{studentId}")
    public ResponseEntity<Void> unenrollStudentFromCourse(@PathVariable Long courseId, @PathVariable Long studentId) {
        courseService.enEnrollStudent(courseId, studentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{courseId}/assign-professor/{professorId}")
    public ResponseEntity<Void> assignProfessorToCourse(@PathVariable Long courseId, @PathVariable Long professorId) {
        courseService.assignProfessor(courseId, professorId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{courseId}/remove-professor/{professorId}")
    public ResponseEntity<Void> removeProfessorFromCourse(@PathVariable Long courseId, @PathVariable Long professorId) {
        courseService.removeProfessor(courseId, professorId);
        return ResponseEntity.ok().build();
    }

}
