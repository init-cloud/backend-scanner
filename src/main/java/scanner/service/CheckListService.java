package scanner.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import scanner.exception.CheckListException;
import scanner.dto.CheckListDetailDto;
import scanner.dto.CheckListSimpleDto;
import scanner.model.CustomRule;
import scanner.repository.CheckListRepository;
import scanner.response.checklist.CheckListDetailResponse;


@Service
@RequiredArgsConstructor
public class CheckListService {
    
    private final CheckListRepository checkListRepository;

    /**
     * 
     * @return
     */
    @Transactional
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
    @Transactional
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

    @Transactional
    public CheckListDetailResponse create(
        CheckListDetailDto data
    ){
        CustomRule rule = CheckListDetailDto.toEntity(data);
        rule = checkListRepository.save(rule);

        return new CheckListDetailResponse(CheckListDetailDto.toDto(rule));
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
        List<Long> ruleIds = new ArrayList<Long>();
                                        
        for(int i = 0 ; i < data.size() ; i++){
            CheckListSimpleDto target = data.get(i);
            
            if(target == null)
                continue;
            else if(target.getCustom() == null || target.getCustom().getCustomDetail() == null){
                checkListRepository.updateRuleOnOff(target.getId(), target.getRuleOnOff());
                ruleIds.add(target.getId());  
            } 
            else{
                checkListRepository.updateRule(target.getId(), target.getRuleOnOff(), target.getCustom().getCustomDetail());
                ruleIds.add(target.getId());  
            }
        }

        List<CustomRule> ruleList = checkListRepository.findByIdIn(ruleIds);

        List<CheckListSimpleDto> ruleDtos = ruleList.stream()
                                    .map(CheckListSimpleDto::new)
                                    .collect(Collectors.toList());

        if(ruleDtos == null || ruleDtos.size() == 0)
            throw new CheckListException("No data modified.");

        return ruleDtos;
    }

    /**
     * 
     * @param data
     * @return
     */
    @Transactional
    public CheckListSimpleDto reset(
        CheckListSimpleDto data
    ){
        try{
            CustomRule target = CheckListSimpleDto.toEntity(data);

            if(target.getId() == null)
                return null;
            
            checkListRepository.resetRule(target.getId());
            
            return new CheckListSimpleDto(checkListRepository.findById(target.getId()));
        }
        catch(CheckListException che){
            return null;
        }

    }
}
