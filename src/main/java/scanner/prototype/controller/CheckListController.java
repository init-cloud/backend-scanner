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
import scanner.prototype.dto.TagDto;
import scanner.prototype.model.CustomRule;
import scanner.prototype.response.ApiResponse;
import scanner.prototype.response.checklist.CheckListDetailResponse;
import scanner.prototype.response.checklist.Response;
import scanner.prototype.service.CheckListService;
import scanner.prototype.service.TagService;
import scanner.prototype.service.user.UserService;

/**
 * "CheckList" is same as "Rule".
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CheckListController {
    
    private final UserService userService;
    private final CheckListService checkListService;
    private final TagService tagService;

    @GetMapping("/checklist")
    public Response<?> retrieveCheckList(
        HttpServletRequest request, 
        HttpServletResponse response
    ){
        CheckListDetailResponse dtos = checkListService.retrieve();

        return Response.success("data", dtos);
    }


    @PostMapping("/checklist")
    public Response<?> modifyCheckListDetail(
        HttpServletRequest request, 
        HttpServletResponse response
    ){

        return Response.success("data", "result");
    }

    @PostMapping("/checklist/state")
    public Response<?> modifyCheckListOnOff(
        HttpServletRequest request, 
        HttpServletResponse response,
        @RequestBody List<CheckListSimpleDto> data
    ){
        List<CheckListSimpleDto> dtos = checkListService.modify(data);

        return Response.success("data", dtos);
    }

    @GetMapping("/tag")
    public Response<?> retrieveTag(
        HttpServletRequest request, 
        HttpServletResponse response
    ){
        List<TagDto> dtos = tagService.retrieve();

        return Response.success("data", dtos);
    }

    @PostMapping("/tag")
    public Response<?> addTag(
        HttpServletRequest request, 
        HttpServletResponse response
    ){
        List<TagDto> dtos = tagService.retrieve();

        return Response.success("data", dtos);
    }
}
