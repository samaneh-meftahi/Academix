package ir.academix.dto;

import infra.dto.BaseDto;
import ir.academix.model.ProfessorEntity;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true)
public class ProfessorDto extends BaseDto<Long, ProfessorEntity,ProfessorDto> {
    private String name;
    private String lastname;

    public ProfessorDto(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }

    @Override
    public ProfessorEntity convert(Class<ProfessorEntity> clazz) {
        return super.convert(clazz)
                .setName(name)
                .setLastname(lastname);
    }
}
