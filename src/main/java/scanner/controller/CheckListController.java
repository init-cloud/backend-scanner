package scanner.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import scanner.dto.rule.CheckListDetail;
import scanner.dto.rule.CheckListSimple;
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
	public ResponseEntity<CommonResponse<CheckListDetail>> getCheckLists() {
		CheckListDetail dtos = checkListService.getOffedCheckLists();

		return ResponseEntity.ok()
			.body(new CommonResponse<>(dtos));
	}

	@ApiOperation(value = "Create Custom Checklist",
		notes = "Create custom new checklist from origin.",
		response = ResponseEntity.class)
	@PostMapping
	public ResponseEntity<CommonResponse<CheckListDetail.Detail>> addCheckList(
		CheckListDetail.Detail data
	) {
		CheckListDetail.Detail dtos = checkListService.addCheckListDetails(data);

		return ResponseEntity.ok()
			.body(new CommonResponse<>(dtos));
	}

	@ApiOperation(value = "Reset Checklist",
		notes = "Reset custom checklist to origin.",
		response = ResponseEntity.class)
	@PostMapping("/reset")
	public ResponseEntity<CommonResponse<CheckListSimple.Simple>> resetCheckList(
		@RequestBody CheckListSimple.Simple data
	) {
		CheckListSimple.Simple dtos = checkListService.resetCheckList(data);

		return ResponseEntity.ok()
			.body(new CommonResponse<>(dtos));
	}

	@ApiOperation(value = "Modify Checklist",
		notes = "Make Custom checklist by modifying origin.",
		response = ResponseEntity.class)
	@PostMapping("/state")
	public ResponseEntity<CommonResponse<List<CheckListSimple.Simple>>> modifyCheckListDetails(
		@RequestBody List<CheckListSimple.Simple> data
	) {
		List<CheckListSimple.Simple> dtos = checkListService.modifyCheckList(data);

		return ResponseEntity.ok()
			.body(new CommonResponse<>(dtos));
	}
}
