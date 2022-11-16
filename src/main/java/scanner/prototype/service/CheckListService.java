package scanner.prototype.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

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

    /**
     * 
     * @return
     */
    public CheckListDetailResponse retrieve(){

        List<CustomRule> ruleList = checkListRepository.findAll();
        List<CheckListDetailDto> ruleDtos = ruleList.stream()
                                            .map(CheckListDetailDto::new)
                                            .collect(Collectors.toList());

        return new CheckListDetailResponse(ruleDtos);
    }

    /**
     * 
     * @return
     */
    public CheckListDetailResponse retrieveOff(){

        List<CustomRule> ruleList = checkListRepository.findByRuleOnOff("n");
        List<CheckListDetailDto> ruleDtos = ruleList.stream()
                                            .map(CheckListDetailDto::new)
                                            .collect(Collectors.toList());

        return new CheckListDetailResponse(ruleDtos);
    }

    /**
     * 
     * @return
     */
    public List<CustomRule> retrieveOffEntity(){
        return checkListRepository.findByRuleOnOff("n");
    }

    /**
     * 
     * @param data
     * @return
     */
    @Transactional
    public List<CheckListSimpleDto> modify(
        List<CheckListSimpleDto> data
    ){
        List<CustomRule> ruleList = data.stream()
                                        .map(CheckListSimpleDto::toEntity)
                                        .collect(Collectors.toList());

        for(int i = 0 ; i < ruleList.size() ; i++) 
            checkListRepository.updateRuleOnOff(ruleList.get(i).getId(), ruleList.get(i).getRuleOnOff());

        List<CheckListSimpleDto> ruleDtos = ruleList.stream()
                                                    .map(CheckListSimpleDto::new)
                                                    .collect(Collectors.toList());

        return ruleDtos;
    }
}
