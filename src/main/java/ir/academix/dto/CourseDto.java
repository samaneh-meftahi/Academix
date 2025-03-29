package ir.academix.dto;

import infra.dto.BaseDto;
import ir.academix.model.CourseEntity;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(chain = true)
public class CourseDto extends BaseDto<Long, CourseEntity, CourseDto> {
    private final String title;
    private final String description;

    public CourseDto(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @Override
    public CourseEntity convert(Class<CourseEntity> clazz) {
        return super.convert(clazz)
                .setTitle(title)
                .setDescription(description);
    }
}

