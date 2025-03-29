package ir.academix.model;

import infra.model.BaseEntity;
import ir.academix.dto.CourseDto;
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
@Table(name = "course")
public class CourseEntity extends BaseEntity<Long,CourseEntity, CourseDto> {
    private String title;
    private String description;

    @ManyToMany(mappedBy = "courses")
    private Set<StudentEntity> students = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "courses_professor",
            joinColumns = @JoinColumn(name = "courses_id"),
            inverseJoinColumns = @JoinColumn(name = "professor_id")
    )
    private Set<ProfessorEntity> professors=new HashSet<>();

    @Override
    public CourseDto convert(Class<CourseDto> clazz) {
        return new CourseDto( this.title, this.description);
    }

}