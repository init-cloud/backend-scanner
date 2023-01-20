package scanner.controller;

import java.util.List;

import io.swagger.annotations.ApiOperation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import scanner.dto.CheckListDetailDto;
import scanner.dto.CheckListSimpleDto;
import scanner.exception.ApiException;
import scanner.exception.CheckListException;
import scanner.response.CommonResponse;
import scanner.response.checklist.CheckListDetailResponse;
import scanner.response.enums.ResponseCode;
import scanner.service.CheckListService;


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
    public ResponseEntity<CommonResponse<CheckListDetailResponse>> retrieveCheckList(){
        CheckListDetailResponse dtos = checkListService.retrieve();

        return ResponseEntity.ok()
                .body(new CommonResponse(dtos));
    }


    @ApiOperation(value = "Create Custom Checklist",
            notes = "Create custom new checklist from origin.",
            response = ResponseEntity.class)
    @PostMapping
    public ResponseEntity<?> createCheckList(
            CheckListDetailDto data
    ){
        CheckListDetailResponse dtos = checkListService.create(data);

        return ResponseEntity.ok()
                .body(new CommonResponse(dtos));
    }

    @ApiOperation(value = "Reset Checklist",
            notes = "Reset custom checklist to origin.",
            response = ResponseEntity.class)
    @PostMapping("/reset")
    public ResponseEntity<?> resetCheckList(
            @RequestBody CheckListSimpleDto data
    ){
        CheckListSimpleDto dtos = checkListService.reset(data);

        if(dtos == null)
            return CommonResponse.toException(new ApiException(ResponseCode.STATUS_4005));

        return ResponseEntity.ok()
                .body(new CommonResponse(dtos));
    }

    @ApiOperation(value = "Modify Checklist",
            notes = "Make Custom checklist by modifying origin.",
            response = ResponseEntity.class)
    @PostMapping("/state")
    public ResponseEntity<?> modifyCheckList(
            @RequestBody List<CheckListSimpleDto> data
    ){
        try{
            if(data == null)
                return CommonResponse.toException(new ApiException(ResponseCode.STATUS_4005));

            List<CheckListSimpleDto> dtos = checkListService.modify(data);

            return ResponseEntity.ok()
                    .body(new CommonResponse(dtos));
        }
        catch(CheckListException che){
            return CommonResponse.toException(new ApiException(ResponseCode.STATUS_4005));
        }
    }
}
