package scanner.prototype.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import scanner.prototype.dto.TagDto;
import scanner.prototype.model.Tag;
import scanner.prototype.repository.TagRepository;


@Service
@RequiredArgsConstructor
public class TagService {
    
    private final TagRepository tagRepository;

    public List<TagDto> retrieve(){
        
        List<Tag> tags = tagRepository.findAll();
        List<TagDto> dtos = tags.stream()
                                .map(TagDto::new)
                                .collect(Collectors.toList());

        return null;
    }

    public List<TagDto> modify(
        List<TagDto> data
    ){
        
        return null;
    }
}
