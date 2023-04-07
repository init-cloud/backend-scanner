package scanner.checklist.service;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import scanner.checklist.dto.CheckListDto;
import scanner.checklist.entity.CustomRuleDetails;
import scanner.common.enums.Language;
import scanner.common.exception.ApiException;
import scanner.checklist.dto.CheckListDetailDto;
import scanner.checklist.entity.CustomRule;
import scanner.checklist.repository.CheckListRepository;
import scanner.common.enums.ResponseCode;

@Service
@RequiredArgsConstructor
public class CheckListService {

	private final CheckListRepository checkListRepository;

	@Transactional
	public CheckListDto.Response getCheckLists(@Nullable String ruleId) {

		List<CustomRule> ruleList;
		List<CheckListDto.Summary> ruleDtos;

		if (ruleId != null) {
			ruleList = checkListRepository.findByRuleIdContains(ruleId);
			ruleDtos = ruleList.stream().map(CheckListDto.Summary::new).collect(Collectors.toList());
		} else {
			ruleList = checkListRepository.findAll();
			ruleDtos = ruleList.stream().map(CheckListDto.Summary::new).collect(Collectors.toList());
		}

		return new CheckListDto.Response(ruleDtos);
	}

	@Transactional
	public CheckListDetailDto.Detail getCheckListDetails(@NonNull String ruleId, @Nullable Language lang) {
		CustomRule rule = checkListRepository.findByRuleId(ruleId)
			.orElseThrow(() -> new ApiException(ResponseCode.INVALID_REQUEST));

		Supplier<Stream<Object>> streamSupplier = () -> Arrays.stream(rule.getRuleDetails().toArray());
		Object ruleDetail = streamSupplier.get()
			.filter(detail -> ((CustomRuleDetails)detail).getLanguage() == lang)
			.findFirst()
			.orElse(streamSupplier.get()
				.filter(detail -> ((CustomRuleDetails)detail).getLanguage() == Language.ENGLISH)
				.findFirst()
				.orElseThrow(() -> new ApiException(ResponseCode.INVALID_REQUEST)));

		return CheckListDetailDto.toDetailsDto(rule, (CustomRuleDetails)ruleDetail);
	}

	@Transactional
	public CheckListDetailDto.Detail addCheckListDetails(CheckListDetailDto.Detail data) {
		CustomRule rule = CheckListDetailDto.toNewEntity(data);
		rule = checkListRepository.save(rule);

		return CheckListDetailDto.toDto(rule);
	}

	@Transactional
	public CheckListDto.Summary modifyCheckList(@NonNull String ruleId, CheckListDto.Modifying data) {
		if (data == null)
			throw new ApiException(ResponseCode.DATA_MISSING);

		if (!ruleId.equals(data.getRule_id()))
			throw new ApiException(ResponseCode.INVALID_REQUEST);

		/**
		 * @Todo 추후 변경된 커스텀 룰이 유효한지 확인하는 로직 필요.
		 **/
		checkListRepository.updateRule(data.getRule_id(), CheckListDto.Modifying.toJsonString(data.getCustom()));

		CustomRule rule = checkListRepository.findByRuleId(ruleId)
			.orElseThrow(() -> new ApiException(ResponseCode.INVALID_REQUEST));

		return new CheckListDto.Summary(rule);
	}

	@Transactional
	public CheckListDto.Summary modifyCheckListAsOnOff(@NonNull String ruleId, CheckListDto.Summary data) {
		if (data == null)
			throw new ApiException(ResponseCode.DATA_MISSING);

		if (!ruleId.equals(data.getRuleId()))
			throw new ApiException(ResponseCode.INVALID_REQUEST);

		checkListRepository.updateRuleOnOff(ruleId, data.getRuleOnOff());

		CustomRule rule = checkListRepository.findByRuleId(ruleId)
			.orElseThrow(() -> new ApiException(ResponseCode.INVALID_REQUEST));

		return new CheckListDto.Summary(rule);
	}

	@Transactional
	public CheckListDto.Summary resetCheckList(CheckListDto.Summary data) {
		if (data.getRuleId() == null)
			throw new ApiException(ResponseCode.DATA_MISSING);

		checkListRepository.resetRule(data.getRuleId());

		CustomRule rule = checkListRepository.findByRuleId(data.getRuleId())
			.orElseThrow(() -> new ApiException(ResponseCode.INVALID_REQUEST));

		return new CheckListDto.Summary(rule);
	}
}

