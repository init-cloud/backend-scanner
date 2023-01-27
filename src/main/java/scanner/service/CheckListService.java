package scanner.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import scanner.exception.ApiException;
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

        List<String> ruleIds = new ArrayList<>();

        for(CheckListSimpleDto target : data){
            
            if(target == null)
                continue;

            if(target.getCustom() == null || target.getCustom().getCustomDetail() == null)
                checkListRepository.updateRuleOnOff(target.getRuleId(), target.getRuleOnOff());

            else
                checkListRepository.updateRule(target.getRuleId(), target.getRuleOnOff(), target.getCustom().getCustomDetail());

            ruleIds.add(target.getRuleId());
        }

        List<CustomRule> ruleList = checkListRepository.findByRuleIdIn(ruleIds);

        List<CheckListSimpleDto> ruleDtos = ruleList.stream()
                                    .map(CheckListSimpleDto::new)
                                    .collect(Collectors.toList());

        if(ruleDtos.isEmpty())
            throw new ApiException(ResponseCode.STATUS_4005);

        return ruleDtos;
    }

    @Transactional
    public CheckListSimpleDto reset(
        CheckListSimpleDto data
    ){
        CustomRule target = CheckListSimpleDto.toEntity(data);

        if(target.getId() == null)
            throw new ApiException(ResponseCode.STATUS_4005);

        checkListRepository.resetRule(target.getRuleId());
        CustomRule rule = checkListRepository.findByRuleId(target.getRuleId())
                .orElseThrow(() -> new ApiException(ResponseCode.STATUS_4005));

        return new CheckListSimpleDto(rule);
    }
}
