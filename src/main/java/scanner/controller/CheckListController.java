package scanner.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import scanner.dto.CheckListDetailDto;
import scanner.dto.CheckListSimpleDto;
import scanner.exception.CheckListException;
import scanner.response.CommonResponse;
import scanner.response.checklist.CheckListDetailResponse;
import scanner.service.CheckListService;


/**
 * "CheckList" is same as "Rule".
 */
@RestController
@RequestMapping("/api/v1/checklist")
@RequiredArgsConstructor
public class CheckListController {
    
    private final CheckListService checkListService;

    /**
     * 룰 조회
     * @return
     */
    @GetMapping
    public ResponseEntity<?> retrieveCheckList(){
        CheckListDetailResponse dtos = checkListService.retrieve();

        return ResponseEntity.ok()
                .body(new CommonResponse(dtos));
    }


    /**
     * 룰 생성
     * @param data
     * @return
     */
    @PostMapping
    public ResponseEntity<?> createCheckList(
        CheckListDetailDto data
    ){
        CheckListDetailResponse dtos = checkListService.create(data);

        return ResponseEntity.ok()
                .body(new CommonResponse(dtos));
    }

    /**
     * 룰 초기화
     * @param data
     * @return
     */
    @PostMapping("/reset")
    public ResponseEntity<?> resetCheckList(
        @RequestBody CheckListSimpleDto data
    ){
        CheckListSimpleDto dtos = checkListService.reset(data);

        if(dtos == null)
            return ResponseEntity.badRequest().body("Error: dtos null.");

        return ResponseEntity.ok()
                .body(new CommonResponse(dtos));
    }

    /**
     * 룰 수정
     * @param data
     * @return
     */
    @PostMapping("/state")
    public ResponseEntity<?> modifyCheckList(
        @RequestBody List<CheckListSimpleDto> data
    ){
        try{
            if(data == null)
                return ResponseEntity.badRequest().body("Error: input null data.");

            List<CheckListSimpleDto> dtos = checkListService.modify(data);

            return ResponseEntity.ok()
                    .body(new CommonResponse(dtos));
        }
        catch(CheckListException che){
            return ResponseEntity.badRequest().body("Error: output null data.");
        }
    }
}
