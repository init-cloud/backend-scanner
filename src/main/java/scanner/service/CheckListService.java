package scanner.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import scanner.exception.ApiException;
import scanner.exception.CheckListException;
import scanner.dto.CheckListDetailDto;
import scanner.dto.CheckListSimpleDto;
import scanner.model.CustomRule;
import scanner.repository.CheckListRepository;
import scanner.response.checklist.CheckListDetailResponse;
import scanner.response.enums.ResponseCode;


@Service
@RequiredArgsConstructor
public class CheckListService {
    
    private final CheckListRepository checkListRepository;

    @Transactional
    public CheckListDetailResponse retrieve(){

        List<CustomRule> ruleList = checkListRepository.findAll();
        List<CheckListDetailDto> ruleDtos = ruleList.stream()
                                            .map(CheckListDetailDto::new)
                                            .collect(Collectors.toList());

        return new CheckListDetailResponse(ruleDtos);
    }

    @Transactional
    public CheckListDetailResponse retrieveOff(){

        List<CustomRule> ruleList = checkListRepository.findByRuleOnOff("n");
        List<CheckListDetailDto> ruleDtos = ruleList.stream()
                                            .map(CheckListDetailDto::new)
                                            .collect(Collectors.toList());

        return new CheckListDetailResponse(ruleDtos);
    }

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

    @Transactional
    public List<CheckListSimpleDto> modify(
        List<CheckListSimpleDto> data
    ){
        if(data == null)
            throw new ApiException(ResponseCode.STATUS_4005);

        List<Long> ruleIds = new ArrayList<>();
                                        
        for(int i = 0 ; i < data.size() ; i++){
            CheckListSimpleDto target = data.get(i);
            
            if(target == null)
                continue;

            if(target.getCustom() == null || target.getCustom().getCustomDetail() == null){
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

        if(ruleDtos == null || ruleDtos.isEmpty())
            throw new ApiException(ResponseCode.STATUS_4005);

        return ruleDtos;
    }

    @Transactional
    public CheckListSimpleDto reset(
        CheckListSimpleDto data
    ){
        try{
            CustomRule target = CheckListSimpleDto.toEntity(data);

            if(target.getId() == null)
                throw new ApiException(ResponseCode.STATUS_4005);
            
            checkListRepository.resetRule(target.getId());
            CustomRule rule = checkListRepository.findById(target.getId())
                    .orElseThrow(() -> new ApiException(ResponseCode.STATUS_4005));

            return new CheckListSimpleDto(rule);
        }
        catch(CheckListException e){
            throw new ApiException(ResponseCode.STATUS_4005);
        }
    }
}
