package scanner.prototype.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import scanner.prototype.dto.CheckListSimpleDto;
import scanner.prototype.model.CustomRule;
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
        HttpServletRequest request, 
        HttpServletResponse response
    ){
        List<CheckListSimpleDto> dtos = checkListService.retrieve();

        return ApiResponse.success("data", dtos);
    }

    @PostMapping("/state")
    public ApiResponse<?> modifyCheckListState(
        HttpServletRequest request, 
        HttpServletResponse response,
        @RequestBody List<CheckListSimpleDto> data
    ){

        return ApiResponse.success("data", "result");
    }

    @PostMapping("/value")
    public ApiResponse<?> modifyCheckListValue(
        HttpServletRequest request, 
        HttpServletResponse response
    ){

        return ApiResponse.success("data", "result");
    }
}
