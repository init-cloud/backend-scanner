package scanner.service.rule;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import scanner.dto.rule.CheckListModifyDto;
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
	public CheckListSimpleDto.Response getCheckListsBySearch(String ruleId) {

		List<CustomRule> ruleList = checkListRepository.findByRuleIdContains(ruleId);
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
		return ruleList.stream().map(CheckListDetailDto.Detail::new).collect(Collectors.toList());
	}

	@Transactional
	public CheckListDetailDto.Detail addCheckListDetails(CheckListDetailDto.Detail data) {
		CustomRule rule = CheckListDetailDto.toEntity(data);
		rule = checkListRepository.save(rule);

		return CheckListDetailDto.toDto(rule);
	}

	@Transactional
	public CheckListSimpleDto.Simple modifyCheckList(@NonNull String ruleId, CheckListModifyDto.Modifying data) {
		if (data == null)
			throw new ApiException(ResponseCode.STATUS_4005);

		if (!ruleId.equals(data.getRule_id()))
			throw new ApiException(ResponseCode.STATUS_4007);

		checkListRepository.updateRule(data.getRule_id(), data.getCustom().toString());

		CustomRule rule = checkListRepository.findByRuleId(ruleId)
			.orElseThrow(() -> new ApiException(ResponseCode.STATUS_4007));

		return new CheckListSimpleDto.Simple(rule);
	}

	@Transactional
	public CheckListSimpleDto.Simple modifyCheckListAsOnOff(@NonNull String ruleId, CheckListSimpleDto.Simple data) {
		if (data == null)
			throw new ApiException(ResponseCode.STATUS_4005);

		if (!ruleId.equals(data.getRuleId()))
			throw new ApiException(ResponseCode.STATUS_4007);

		checkListRepository.updateRuleOnOff(ruleId, data.getRuleOnOff());

		CustomRule rule = checkListRepository.findByRuleId(ruleId)
			.orElseThrow(() -> new ApiException(ResponseCode.STATUS_4002));

		return new CheckListSimpleDto.Simple(rule);
	}

	@Transactional
	public CheckListSimpleDto.Simple resetCheckList(CheckListSimpleDto.Simple data) {
		if (data.getRuleId() == null)
			throw new ApiException(ResponseCode.STATUS_4005);

		checkListRepository.resetRule(data.getRuleId());

		CustomRule rule = checkListRepository.findByRuleId(data.getRuleId())
			.orElseThrow(() -> new ApiException(ResponseCode.STATUS_4005));

		return new CheckListSimpleDto.Simple(rule);
	}
}
