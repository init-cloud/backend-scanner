package scanner.prototype.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import scanner.prototype.dto.CheckListSimpleDto;
import scanner.prototype.model.CustomRule;
import scanner.prototype.repository.CheckListRepository;


@Service
@RequiredArgsConstructor
public class CheckListService {
    
    private final CheckListRepository checkListRepository;

    public List<CheckListSimpleDto> retrieve(){

        List<CustomRule> ruleList = checkListRepository.findAll();
        List<CheckListSimpleDto> ruleDtos = ruleList.stream()
                                                    .map(CheckListSimpleDto::new)
                                                    .collect(Collectors.toList());

        return ruleDtos;
    }

    public List<CheckListSimpleDto> modify(){

        List<CustomRule> ruleList = checkListRepository.findAll();
        List<CheckListSimpleDto> ruleDtos = ruleList.stream()
                                                    .map(CheckListSimpleDto::new)
                                                    .collect(Collectors.toList());

        return ruleDtos;
    }
}
