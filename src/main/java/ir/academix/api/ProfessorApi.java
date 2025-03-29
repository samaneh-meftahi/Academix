package ir.academix.api;

import ir.academix.dto.CourseDto;
import ir.academix.dto.ProfessorDto;
import ir.academix.dto.StudentDto;
import ir.academix.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professors")
public class ProfessorApi {
    private final ProfessorService professorService;

    @Autowired
    public ProfessorApi(ProfessorService professorService) {
        this.professorService = professorService;
    }



    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDto> getProfessorById(@PathVariable Long id) {
        return ResponseEntity.ok(professorService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProfessorDto>> getAllProfessors() {
        return ResponseEntity.ok(professorService.findAll());
    }

    @PostMapping
    public ResponseEntity<ProfessorDto> createProfessor(@RequestBody ProfessorDto professorDto) {
        return ResponseEntity.ok(professorService.saveOrUpdate(professorDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorDto> updateProfessor(@PathVariable Long id, @RequestBody ProfessorDto professorDto) {
        return ResponseEntity.ok(professorService.update(id, professorDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable Long id) {
        professorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProfessorDto>> searchProfessors(@RequestParam(required = false) String name,
                                                               @RequestParam(required = false) String lastName) {
        if (name != null && lastName != null) {
            return ResponseEntity.ok(professorService.findByNameAndLastName(name, lastName));
        } else if (name != null) {
            return ResponseEntity.ok(professorService.findByName(name));
        } else if (lastName != null) {
            return ResponseEntity.ok(professorService.findByLastName(lastName));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/{professorId}/assign/{courseId}")
    public ResponseEntity<Void> assignProfessorToCourse(@PathVariable Long professorId, @PathVariable Long courseId) {
        professorService.assignToCourse(professorId, courseId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{professorId}/remove/{courseId}")
    public ResponseEntity<Void> removeProfessorFromCourse(@PathVariable Long professorId, @PathVariable Long courseId) {
        professorService.removeFromCourse(professorId, courseId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{professorId}/courses")
    public ResponseEntity<List<CourseDto>> getProfessorCourses(@PathVariable Long professorId) {
        return ResponseEntity.ok(professorService.getAssignedCourses(professorId));
    }

    @GetMapping("/{professorId}/students")
    public ResponseEntity<List<StudentDto>> getProfessorStudents(@PathVariable Long professorId) {
        return ResponseEntity.ok(professorService.getProfessorStudents(professorId));
    }
}
