package scanner.service.rule;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import scanner.dto.rule.TagDto;
import scanner.model.rule.Tag;
import scanner.repository.TagRepository;

@Service
@RequiredArgsConstructor
public class TagService {

	private final TagRepository tagRepository;

	public List<TagDto> retrieve() {

		List<Tag> tags = tagRepository.findAll();
		return tags.stream()
			.map(TagDto::new)
			.collect(Collectors.toList());
	}
}
