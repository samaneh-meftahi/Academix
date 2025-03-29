package ir.academix.model;
import ir.academix.dto.ProfessorDto;
import infra.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "professors")
public class ProfessorEntity extends BaseEntity<Long,ProfessorEntity,ProfessorDto> {

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "lastname",nullable = false)
    private String lastname;

    @ManyToMany
    @JoinTable(
            name = "Course_professor",
            joinColumns = @JoinColumn(name = "professor_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<CourseEntity> courses = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name= "student_professor",
            joinColumns = @JoinColumn(name = "proffessor_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<StudentEntity> students=new HashSet<>();

    @Override
    public ProfessorDto convert(Class<ProfessorDto> clazz) {
        return new ProfessorDto(this.getName(),this.getLastname());
    }

}
