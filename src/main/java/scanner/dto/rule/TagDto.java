package scanner.dto.rule;

import lombok.*;
import scanner.model.rule.Tag;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TagDto {
	private Long tagId;
	private String tag;

	public TagDto(final Tag tag) {
		this.tagId = tag.getId();
		this.tag = tag.getTagName();
	}

	public static TagDto toDto(final Tag entity) {
		return TagDto.builder()
			.tagId(entity.getId())
			.tag(entity.getTagName())
			.build();
	}

	public static Tag toEntity(final TagDto dto) {
		return Tag.builder()
			.tagName(dto.getTag())
			.build();
	}

	public static TagDto toString(final Tag entity) {
		return TagDto.builder()
			.tagId(entity.getId())
			.tag(entity.getTagName())
			.build();
	}
}
