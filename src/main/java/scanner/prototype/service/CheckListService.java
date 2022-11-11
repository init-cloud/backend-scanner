package scanner.prototype.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import scanner.prototype.dto.CheckListDetailDto;
import scanner.prototype.dto.CheckListSimpleDto;
import scanner.prototype.model.CustomRule;
import scanner.prototype.repository.CheckListRepository;
import scanner.prototype.response.checklist.CheckListDetailResponse;


@Service
@RequiredArgsConstructor
public class CheckListService {
    
    private final CheckListRepository checkListRepository;

    public CheckListDetailResponse retrieve(){

        List<CustomRule> ruleList = checkListRepository.findAll();
        List<CheckListDetailDto> ruleDtos = ruleList.stream()
                                            .map(CheckListDetailDto::new)
                                            .collect(Collectors.toList());

        return new CheckListDetailResponse(ruleDtos);
    }

    public List<CheckListSimpleDto> modify(
        List<CheckListSimpleDto> data
    ){

        List<CustomRule> ruleList = data.stream()
                                        .map(CheckListSimpleDto::toEntity)
                                        .collect(Collectors.toList());

        for(int i = 0 ; i < ruleList.size() ; i++) 
            checkListRepository.save(ruleList.get(i));

        List<CheckListSimpleDto> ruleDtos = ruleList.stream()
                                                    .map(CheckListSimpleDto::new)
                                                    .collect(Collectors.toList());

        return ruleDtos;
    }
}
