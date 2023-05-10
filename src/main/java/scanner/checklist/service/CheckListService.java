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
import scanner.common.exception.ApiAuthException;
import scanner.common.exception.ApiException;
import scanner.checklist.dto.CheckListDetailDto;
import scanner.common.enums.ResponseCode;
import scanner.security.provider.JwtTokenProvider;
import scanner.user.entity.User;
import scanner.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CheckListService {

	private final UsedCheckListRepository usedCheckListRepository;
	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;

	@Transactional
	public CheckListDto.Response getCheckLists(@Nullable String ruleName) {

		User currentUser = getCurrentUser();

		List<UsedRule> ruleList;
		List<CheckListDto.Summary> ruleDtos;

		if (ruleName != null) {
			ruleList = usedCheckListRepository.findByUserAndRuleNameContains(currentUser, ruleName);
			ruleDtos = ruleList.stream().map(CheckListDto.Summary::new).collect(Collectors.toList());
		} else {
			ruleList = usedCheckListRepository.findAll();
			ruleDtos = ruleList.stream().map(CheckListDto.Summary::new).collect(Collectors.toList());
		}

		return new CheckListDto.Response(ruleDtos);
	}

	@Transactional
	public CheckListDetailDto.Detail getCheckListDetails(@NonNull String ruleName, @Nullable Language lang) {
		User currentUser = getCurrentUser();

		UsedRule rule = usedCheckListRepository.findByUserAndRuleName(currentUser, ruleName)
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

		User currentUser = getCurrentUser();
		usedCheckListRepository.updateRule(currentUser.getId(), data.getRuleName(),
			CheckListDto.Modifying.toJsonString(data.getCustom()));

		UsedRule rule = usedCheckListRepository.findByUserAndRuleName(currentUser, ruleId)
			.orElseThrow(() -> new ApiException(ResponseCode.INVALID_REQUEST));

		return new CheckListDto.Summary(rule);
	}

	@Transactional
	public CheckListDto.Summary modifyCheckListAsOnOff(@NonNull String ruleId, CheckListDto.Summary data) {
		if (data == null)
			throw new ApiException(ResponseCode.DATA_MISSING);

		if (!ruleId.equals(data.getRuleName()))
			throw new ApiException(ResponseCode.INVALID_REQUEST);

		User currentUser = getCurrentUser();
		usedCheckListRepository.updateRuleOnOff(currentUser.getId(), ruleId, data.getRuleOnOff());

		UsedRule rule = usedCheckListRepository.findByUserAndRuleName(currentUser, ruleId)
			.orElseThrow(() -> new ApiException(ResponseCode.INVALID_REQUEST));

		return new CheckListDto.Summary(rule);
	}

	@Transactional
	public CheckListDto.Summary resetCheckList(CheckListDto.Summary data) {
		if (data.getRuleName() == null)
			throw new ApiException(ResponseCode.DATA_MISSING);

		User currentUser = getCurrentUser();

		usedCheckListRepository.resetRule(currentUser.getId(), data.getRuleName());

		UsedRule rule = usedCheckListRepository.findByUserAndRuleName(currentUser, data.getRuleName())
			.orElseThrow(() -> new ApiException(ResponseCode.INVALID_REQUEST));

		return new CheckListDto.Summary(rule);
	}

	private User getCurrentUser() {
		String username = jwtTokenProvider.getUsername();
		return userRepository.findByUsername(username)
			.orElseThrow(() -> new ApiAuthException(ResponseCode.INVALID_USER));
	}
}

