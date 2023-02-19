package scanner.service.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import scanner.exception.ApiException;
import scanner.dto.rule.CheckListDetailDto;
import scanner.dto.rule.CheckListSimpleDto;
import scanner.model.rule.CustomRule;
import scanner.repository.CheckListRepository;
import scanner.common.enums.ResponseCode;

@Service
@RequiredArgsConstructor
public class CheckListService {

	private final CheckListRepository checkListRepository;

	@Transactional
	public CheckListSimpleDto.Response getCheckLists() {

		List<CustomRule> ruleList = checkListRepository.findAll();
		List<CheckListSimpleDto.Simple> ruleDtos = ruleList.stream()
			.map(CheckListSimpleDto.Simple::new)
			.collect(Collectors.toList());

		return new CheckListSimpleDto.Response(ruleDtos);
	}

	@Transactional
	public CheckListDetailDto.Detail getCheckListDetails(String ruleId) {

		CustomRule rule = checkListRepository.findByRuleId(ruleId).orElseThrow();

		return new CheckListDetailDto.Detail(rule);
	}

	public List<CustomRule> getOffedCheckList() {
		return checkListRepository.findByRuleOnOff("n");
	}

	@Transactional
	public List<CheckListDetailDto.Detail> getCheckListDetailsList() {

		List<CustomRule> ruleList = checkListRepository.findAll();
		return ruleList.stream()
			.map(CheckListDetailDto.Detail::new)
			.collect(Collectors.toList());
	}

	@Transactional
	public CheckListDetailDto.Detail addCheckListDetails(
		CheckListDetailDto.Detail data
	) {
		CustomRule rule = CheckListDetailDto.toEntity(data);
		rule = checkListRepository.save(rule);

		return CheckListDetailDto.toDto(rule);
	}

	@Transactional
	public List<CheckListSimpleDto.Simple> modifyCheckList(
		List<CheckListSimpleDto.Simple> data
	) {
		if (data == null)
			throw new ApiException(ResponseCode.STATUS_4005);

		List<String> ruleIds = new ArrayList<>();

		for (CheckListSimpleDto.Simple target : data) {

			if (target == null)
				continue;

			if (target.getCustom() == null || target.getCustom().getCustomDetail() == null)
				checkListRepository.updateRuleOnOff(target.getRuleId(), target.getRuleOnOff());

			else
				checkListRepository.updateRule(target.getRuleId(), target.getRuleOnOff(),
					target.getCustom().getCustomDetail());

			ruleIds.add(target.getRuleId());
		}

		List<CustomRule> ruleList = checkListRepository.findByRuleIdIn(ruleIds);

		List<CheckListSimpleDto.Simple> ruleDtos = ruleList.stream()
			.map(CheckListSimpleDto.Simple::new)
			.collect(Collectors.toList());

		if (ruleDtos.isEmpty())
			throw new ApiException(ResponseCode.STATUS_4005);

		return ruleDtos;
	}

	@Transactional
	public CheckListSimpleDto.Simple resetCheckList(
		CheckListSimpleDto.Simple data
	) {
		CustomRule target = CheckListSimpleDto.toEntity(data);

		if (target.getId() == null)
			throw new ApiException(ResponseCode.STATUS_4005);

		checkListRepository.resetRule(target.getRuleId());
		CustomRule rule = checkListRepository.findByRuleId(target.getRuleId())
			.orElseThrow(() -> new ApiException(ResponseCode.STATUS_4005));

		return new CheckListSimpleDto.Simple(rule);
	}
}
