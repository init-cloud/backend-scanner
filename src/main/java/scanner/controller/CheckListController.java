package scanner.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import scanner.dto.rule.CheckListDetailDto;
import scanner.dto.rule.CheckListModifyDto;
import scanner.dto.rule.CheckListSimpleDto;
import scanner.dto.CommonResponse;
import scanner.service.rule.CheckListService;

@ApiOperation("Checklist API. Checklist is same as Rule.")
@RestController
@RequestMapping("/api/v1/checklist")
@RequiredArgsConstructor
public class CheckListController {

	private final CheckListService checkListService;

	@ApiOperation(value = "Retrieve Checklist", notes = "Retrieve all checklists. You can use Search", response = CommonResponse.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class)})
	@GetMapping
	public CommonResponse<CheckListSimpleDto.Response> checkLists() {
		CheckListSimpleDto.Response dto = checkListService.getCheckLists(null);

		return new CommonResponse<>(dto);
	}

	@ApiOperation(value = "Create Custom Checklist", notes = "Create custom new checklist from origin.", response = CommonResponse.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class)})
	@PostMapping
	public CommonResponse<CheckListDetailDto.Detail> addCheckList(CheckListDetailDto.Detail data) {
		CheckListDetailDto.Detail dto = checkListService.addCheckListDetails(data);

		return new CommonResponse<>(dto);
	}

	@ApiOperation(value = "Retrieve Checklist Details", notes = "Retrieve checklists.", response = CommonResponse.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "ruleId", paramType = "path", value = "Checklist(rule) ID", required = true, dataTypeClass = String.class, example = "CKV_NCP_1")})
	@GetMapping("/{ruleId}")
	public CommonResponse<CheckListDetailDto.Detail> checkListDetails(@PathVariable String ruleId) {
		CheckListDetailDto.Detail dto = checkListService.getCheckListDetails(ruleId);

		return new CommonResponse<>(dto);
	}

	@ApiOperation(value = "Modify Checklist", notes = "Make Custom checklist by modifying origin.", response = CommonResponse.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "ruleId", paramType = "path", value = "ruleId.", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "CheckListSimpleDto.Simple", paramType = "body", value = "Modify ruleOnOff n to y, y to n", required = true, dataTypeClass = CheckListModifyDto.Modifying.class)})
	@PatchMapping("/{ruleId}")
	public CommonResponse<CheckListSimpleDto.Simple> modifyCheckListDetails(@PathVariable String ruleId,
		@RequestBody CheckListModifyDto.Modifying data) {
		CheckListSimpleDto.Simple dto = checkListService.modifyCheckList(ruleId, data);

		return new CommonResponse<>(dto);
	}

	@ApiOperation(value = "Modify Checklist On/Off State", notes = "Make Checklist State On/Off.", response = CommonResponse.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "ruleId", paramType = "path", value = "ruleId.", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "CheckListSimpleDto.Simple", paramType = "body", value = "Modify ruleOnOff n to y, y to n", required = true, dataTypeClass = CheckListModifyDto.State.class)})
	@PatchMapping("/state/{ruleId}")
	public CommonResponse<CheckListSimpleDto.Simple> modifyCheckListOnOff(@PathVariable String ruleId,
		@RequestBody CheckListSimpleDto.Simple data) {
		CheckListSimpleDto.Simple dto = checkListService.modifyCheckListAsOnOff(ruleId, data);

		return new CommonResponse<>(dto);
	}

	@ApiOperation(value = "Reset Checklist", notes = "Reset custom checklist to origin.", response = CommonResponse.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "CheckListSimpleDto.Simple", paramType = "body", value = "Body need ruleId.", required = true, dataTypeClass = CheckListSimpleDto.Simple.class)})
	@PostMapping("/state")
	public CommonResponse<CheckListSimpleDto.Simple> resetCheckList(@RequestBody CheckListSimpleDto.Simple data) {
		CheckListSimpleDto.Simple dto = checkListService.resetCheckList(data);

		return new CommonResponse<>(dto);
	}
}
