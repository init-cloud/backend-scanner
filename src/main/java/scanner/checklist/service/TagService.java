package scanner.checklist.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import scanner.checklist.dto.TagDto;
import scanner.checklist.entity.Tag;
import scanner.checklist.repository.TagRepository;

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
