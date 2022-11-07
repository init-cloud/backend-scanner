package scanner.prototype.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import scanner.prototype.dto.CheckListSimpleDto;
import scanner.prototype.response.ApiResponse;
import scanner.prototype.service.CheckListService;
import scanner.prototype.service.user.UserService;

/**
 * "CheckList" is same as "Rule".
 */
@RestController
@RequestMapping("/api/v1/checklist")
@RequiredArgsConstructor
public class CheckListController {
    
    private final UserService userService;
    private final CheckListService checkListService;

    @GetMapping
    public ApiResponse<?> retrieveCheckList(

    ){

        return ApiResponse.success("data", "sesult");
    }

    @PostMapping("/checklist/")
    public ApiResponse<?> modifyCheckListState(
        @RequestBody List<CheckListSimpleDto> request
    ){

        return ApiResponse.success("data", "sesult");
    }

    @PostMapping()
    public ApiResponse<?> modifyCheckListValue(

    ){

        return ApiResponse.success("data", "sesult");
    }
}
