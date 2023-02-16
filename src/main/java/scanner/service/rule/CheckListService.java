package scanner.service.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import scanner.exception.ApiException;
import scanner.dto.rule.CheckListDetail;
import scanner.dto.rule.CheckListSimple;
import scanner.model.rule.CustomRule;
import scanner.repository.CheckListRepository;
import scanner.common.enums.ResponseCode;

@Service
@RequiredArgsConstructor
public class CheckListService {

	private final CheckListRepository checkListRepository;

	@Transactional
	public CheckListDetail getCheckListDetails() {

		List<CustomRule> ruleList = checkListRepository.findAll();
		List<CheckListDetail.Detail> ruleDtos = ruleList.stream()
			.map(CheckListDetail.Detail::new)
			.collect(Collectors.toList());

		return new CheckListDetail(ruleDtos);
	}

	@Transactional
	public CheckListDetail getOffedCheckLists() {

		List<CustomRule> ruleList = checkListRepository.findByRuleOnOff("n");
		List<CheckListDetail.Detail> ruleDtos = ruleList.stream()
			.map(CheckListDetail.Detail::new)
			.collect(Collectors.toList());

		return new CheckListDetail(ruleDtos);
	}

	public List<CustomRule> getOffedCheckList() {
		return checkListRepository.findByRuleOnOff("n");
	}

	@Transactional
	public CheckListDetail.Detail addCheckListDetails(
		CheckListDetail.Detail data
	) {
		CustomRule rule = CheckListDetail.toEntity(data);
		rule = checkListRepository.save(rule);

		return CheckListDetail.toDto(rule);
	}

	@Transactional
	public List<CheckListSimple.Simple> modifyCheckList(
		List<CheckListSimple.Simple> data
	) {
		if (data == null)
			throw new ApiException(ResponseCode.STATUS_4005);

		List<String> ruleIds = new ArrayList<>();

		for (CheckListSimple.Simple target : data) {

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

		List<CheckListSimple.Simple> ruleDtos = ruleList.stream()
			.map(CheckListSimple.Simple::new)
			.collect(Collectors.toList());

		if (ruleDtos.isEmpty())
			throw new ApiException(ResponseCode.STATUS_4005);

		return ruleDtos;
	}

	@Transactional
	public CheckListSimple.Simple resetCheckList(
		CheckListSimple.Simple data
	) {
		CustomRule target = CheckListSimple.toEntity(data);

		if (target.getId() == null)
			throw new ApiException(ResponseCode.STATUS_4005);

		checkListRepository.resetRule(target.getRuleId());
		CustomRule rule = checkListRepository.findByRuleId(target.getRuleId())
			.orElseThrow(() -> new ApiException(ResponseCode.STATUS_4005));

		return new CheckListSimple.Simple(rule);
	}
}
