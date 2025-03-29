package ir.academix.model;

import infra.model.BaseEntity;
import ir.academix.dto.StudentDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.*;

@Entity
@Accessors(chain = true)
@Getter
@Setter
public class StudentEntity extends BaseEntity
        <Long,StudentEntity, StudentDto> {

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "lastname",nullable = false)
    private String lastname;

    @ElementCollection
    @CollectionTable(
            name = "Student_grades",
            joinColumns =@JoinColumn(name="Student_id")
    )
    @Column(name = "grades")
     private List<Double> grades;

    @ManyToMany
    @JoinTable(
            name = "course_student",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<CourseEntity> courses = new HashSet<>();
    

    @ManyToMany
    @JoinTable(
            name = "professor_student",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "professor_id")
    )
    private Set<ProfessorEntity> professors=new HashSet<>();

    @Override
    public StudentDto convert(Class<StudentDto> clazz) {
        return new StudentDto(this.getName(),this.getLastname());
    }


}
