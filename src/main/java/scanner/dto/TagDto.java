package scanner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import scanner.model.Tag;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagDto {
    private Long tagId;
    private String tag;

    public TagDto(final Tag tag){
        this.tagId = tag.getId();
        this.tag = tag.getTag();
    }

    public static TagDto toDto(final Tag entity) {
        return TagDto.builder()
                .tagId(entity.getId())
                .tag(entity.getTag())
                .build();
    }

    public static Tag toEntity(final TagDto dto){
        return Tag.builder()
                .id(dto.getTagId())
                .tag(dto.getTag())
                .build();
    }

    public static TagDto toString(final Tag entity) {
        return TagDto.builder()
                .tagId(entity.getId())
                .tag(entity.getTag())
                .build();
    }
}
