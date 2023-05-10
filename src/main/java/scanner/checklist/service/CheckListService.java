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
import scanner.checklist.entity.UsedRule;
import scanner.checklist.repository.UsedCheckListRepository;
import scanner.common.enums.Language;
import scanner.common.exception.ApiException;
import scanner.checklist.dto.CheckListDetailDto;
import scanner.common.enums.ResponseCode;

@Service
@RequiredArgsConstructor
public class CheckListService {

	private final UsedCheckListRepository usedCheckListRepository;

	@Transactional
	public CheckListDto.Response getCheckLists(@Nullable String ruleName) {

		List<UsedRule> ruleList;
		List<CheckListDto.Summary> ruleDtos;

		if (ruleName != null) {
			ruleList = usedCheckListRepository.findByRuleNameContains(ruleName);
			ruleDtos = ruleList.stream().map(CheckListDto.Summary::new).collect(Collectors.toList());
		} else {
			ruleList = usedCheckListRepository.findAll();
			ruleDtos = ruleList.stream().map(CheckListDto.Summary::new).collect(Collectors.toList());
		}

		return new CheckListDto.Response(ruleDtos);
	}

	@Transactional
	public CheckListDetailDto.Detail getCheckListDetails(@NonNull String ruleName, @Nullable Language lang) {
		UsedRule rule = usedCheckListRepository.findByRuleName(ruleName)
			.orElseThrow(() -> new ApiException(ResponseCode.INVALID_REQUEST));

		Supplier<Stream<Object>> streamSupplier = () -> Arrays.stream(rule.getOriginRule().getRuleDetails().toArray());

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
	public CheckListDto.Summary modifyCheckList(@NonNull String ruleId, CheckListDto.Modifying data) {
		if (data == null)
			throw new ApiException(ResponseCode.DATA_MISSING);

		if (!ruleId.equals(data.getRuleName()))
			throw new ApiException(ResponseCode.INVALID_REQUEST);

		/**
		 * @Todo 추후 변경된 커스텀 룰이 유효한지 확인하는 로직 필요.
		 **/
		usedCheckListRepository.updateRule(data.getRuleName(), CheckListDto.Modifying.toJsonString(data.getCustom()));

		UsedRule rule = usedCheckListRepository.findByRuleName(ruleId)
			.orElseThrow(() -> new ApiException(ResponseCode.INVALID_REQUEST));

		return new CheckListDto.Summary(rule);
	}

	@Transactional
	public CheckListDto.Summary modifyCheckListAsOnOff(@NonNull String ruleId, CheckListDto.Summary data) {
		if (data == null)
			throw new ApiException(ResponseCode.DATA_MISSING);

		if (!ruleId.equals(data.getRuleName()))
			throw new ApiException(ResponseCode.INVALID_REQUEST);

		usedCheckListRepository.updateRuleOnOff(ruleId, data.getRuleOnOff());

		UsedRule rule = usedCheckListRepository.findByRuleName(ruleId)
			.orElseThrow(() -> new ApiException(ResponseCode.INVALID_REQUEST));

		return new CheckListDto.Summary(rule);
	}

	@Transactional
	public CheckListDto.Summary resetCheckList(CheckListDto.Summary data) {
		if (data.getRuleName() == null)
			throw new ApiException(ResponseCode.DATA_MISSING);

		usedCheckListRepository.resetRule(data.getRuleName());

		UsedRule rule = usedCheckListRepository.findByRuleName(data.getRuleName())
			.orElseThrow(() -> new ApiException(ResponseCode.INVALID_REQUEST));

		return new CheckListDto.Summary(rule);
	}
}

