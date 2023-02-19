package scanner.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import scanner.dto.rule.CheckListDetailDto;
import scanner.dto.rule.CheckListSimpleDto;
import scanner.dto.CommonResponse;
import scanner.service.rule.CheckListService;

import java.util.List;

@ApiOperation("Checklist API. Checklist is same as Rule.")
@RestController
@RequestMapping("/api/v1/checklist")
@RequiredArgsConstructor
public class CheckListController {

	private final CheckListService checkListService;

	@ApiOperation(value = "Retrieve Checklist",
		notes = "Retrieve all checklists.",
		response = ResponseEntity.class)
	@GetMapping
	public ResponseEntity<CommonResponse<CheckListSimpleDto.Response>> checkLists() {
		CheckListSimpleDto.Response dtos = checkListService.getCheckLists();

		return ResponseEntity.ok()
			.body(new CommonResponse<>(dtos));
	}

	@ApiOperation(value = "Retrieve Checklist Details",
		notes = "Retrieve checklists.",
		response = ResponseEntity.class)
	@GetMapping("/{ruleId}")
	public ResponseEntity<CommonResponse<CheckListDetailDto.Detail>> checkListDetails(@PathVariable String ruleId) {
		CheckListDetailDto.Detail dtos = checkListService.getCheckListDetails(ruleId);

		return ResponseEntity.ok()
			.body(new CommonResponse<>(dtos));
	}

	@ApiOperation(value = "Create Custom Checklist",
		notes = "Create custom new checklist from origin.",
		response = ResponseEntity.class)
	@PostMapping
	public ResponseEntity<CommonResponse<CheckListDetailDto.Detail>> addCheckList(
		CheckListDetailDto.Detail data
	) {
		CheckListDetailDto.Detail dtos = checkListService.addCheckListDetails(data);

		return ResponseEntity.ok()
			.body(new CommonResponse<>(dtos));
	}

	@ApiOperation(value = "Reset Checklist",
		notes = "Reset custom checklist to origin.",
		response = ResponseEntity.class)
	@PostMapping("/reset")
	public ResponseEntity<CommonResponse<CheckListSimpleDto.Simple>> resetCheckList(
		@RequestBody CheckListSimpleDto.Simple data
	) {
		CheckListSimpleDto.Simple dtos = checkListService.resetCheckList(data);

		return ResponseEntity.ok()
			.body(new CommonResponse<>(dtos));
	}

	@ApiOperation(value = "Modify Checklist",
		notes = "Make Custom checklist by modifying origin.",
		response = ResponseEntity.class)
	@PostMapping("/state")
	public ResponseEntity<CommonResponse<List<CheckListSimpleDto.Simple>>> modifyCheckListDetails(
		@RequestBody List<CheckListSimpleDto.Simple> data
	) {
		List<CheckListSimpleDto.Simple> dtos = checkListService.modifyCheckList(data);

		return ResponseEntity.ok()
			.body(new CommonResponse<>(dtos));
	}
}
