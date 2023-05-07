package scanner.checklist.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import scanner.checklist.dto.CheckListDetailDto;
import scanner.checklist.dto.CheckListDto;
import scanner.checklist.service.CheckListService;
import scanner.common.dto.ResponseDto;
import scanner.common.enums.Language;

import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@ApiOperation("Checklist API. Checklist is same as Rule.")
@RestController
@RequestMapping("/api/v1/checklist")
@RequiredArgsConstructor
public class CheckListController {

	private final CheckListService checkListService;

	@ApiOperation(value = "Retrieve Checklist", notes = "Retrieve all checklists. You can use Search", response = ResponseDto.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class)})
	@GetMapping
	public ResponseDto<CheckListDto.Response> checkLists() {
		CheckListDto.Response dto = checkListService.getCheckLists(null);

		return new ResponseDto<>(dto);
	}

	@ApiOperation(value = "Create Custom Checklist", notes = "Create custom new checklist from origin.", response = ResponseDto.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class)})
	@PostMapping
	public ResponseDto<CheckListDetailDto.Detail> addCheckList(CheckListDetailDto.Detail data) {
		CheckListDetailDto.Detail dto = checkListService.addCheckListDetails(data);

		return new ResponseDto<>(dto);
	}

	@ApiOperation(value = "Retrieve Checklist Details", notes = "Retrieve checklists.", response = ResponseDto.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "ruleId", paramType = "path", value = "Checklist(rule) ID", required = true, dataTypeClass = String.class, example = "CKV_NCP_1"),
		@ApiImplicitParam(name = "lang", paramType = "query", value = "eng, kor", dataTypeClass = String.class)})
	@GetMapping("/{ruleId}")
	public ResponseDto<CheckListDetailDto.Detail> checkListDetails(@PathVariable String ruleId,
		@Nullable @RequestParam String lang) {
		CheckListDetailDto.Detail dto = checkListService.getCheckListDetails(ruleId, Language.of(lang));

		return new ResponseDto<>(dto);
	}

	@ApiOperation(value = "Modify Checklist", notes = "Make Custom checklist by modifying origin.", response = ResponseDto.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "ruleId", paramType = "path", value = "ruleId.", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "CheckListDto.Simple", paramType = "body", value = "Modify ruleOnOff n to y, y to n", required = true, dataTypeClass = CheckListDto.Modifying.class)})
	@PatchMapping("/{ruleId}")
	public ResponseDto<CheckListDto.Summary> modifyCheckListDetails(@PathVariable String ruleId,
		@RequestBody CheckListDto.Modifying data) {
		CheckListDto.Summary dto = checkListService.modifyCheckList(ruleId, data);

		return new ResponseDto<>(dto);
	}

	@ApiOperation(value = "Modify Checklist On/Off State", notes = "Make Checklist State On/Off.", response = ResponseDto.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "ruleId", paramType = "path", value = "ruleId.", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "CheckListDto.RuleState", paramType = "body", value = "Modify ruleOnOff n to y, y to n", required = true, dataTypeClass = CheckListDto.RuleState.class)})
	@PatchMapping("/state/{ruleId}")
	public ResponseDto<CheckListDto.Summary> modifyCheckListOnOff(@PathVariable String ruleId,
		@RequestBody CheckListDto.Summary data) {
		CheckListDto.Summary dto = checkListService.modifyCheckListAsOnOff(ruleId, data);

		return new ResponseDto<>(dto);
	}

	@ApiOperation(value = "Reset Checklist", notes = "Reset custom checklist to origin.", response = ResponseDto.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "CheckListDto.Simple", paramType = "body", value = "Body need ruleId.", required = true, dataTypeClass = CheckListDto.Summary.class)})
	@PostMapping("/state")
	public ResponseDto<CheckListDto.Summary> resetCheckList(@RequestBody CheckListDto.Summary data) {
		CheckListDto.Summary dto = checkListService.resetCheckList(data);

		return new ResponseDto<>(dto);
	}
}

