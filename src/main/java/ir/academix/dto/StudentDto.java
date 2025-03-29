package ir.academix.dto;
import infra.dto.BaseDto;
import ir.academix.model.StudentEntity;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true)
public class StudentDto extends BaseDto<Long, StudentEntity,StudentDto> {
    private String name;
    private String lastname;

    public StudentDto(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }

    @Override
    public StudentEntity convert(Class<StudentEntity> clazz) {
        return super.convert(clazz)
                .setName(name)
                .setLastname(lastname);
    }
}
