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
import scanner.prototype.dto.CheckListDetailDto;
import scanner.prototype.dto.CheckListSimpleDto;
import scanner.prototype.dto.TagDto;
import scanner.prototype.response.checklist.CheckListDetailResponse;
import scanner.prototype.response.checklist.Response;
import scanner.prototype.service.CheckListService;
import scanner.prototype.service.TagService;

/**
 * "CheckList" is same as "Rule".
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CheckListController {
    
    private final CheckListService checkListService;
    private final TagService tagService;

    /**
     * 룰 조회
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/checklist")
    public Response<?> retrieveCheckList(
        HttpServletRequest request, 
        HttpServletResponse response
    ){
        CheckListDetailResponse dtos = checkListService.retrieve();

        return Response.success("data", dtos);
    }


    /**
     * 보류 - 룰 생성
     * @param request
     * @param response
     * @param data
     * @return
     */
    @PostMapping("/checklist")
    public Response<?> createCheckList(
        HttpServletRequest request, 
        HttpServletResponse response,
        CheckListDetailDto data
    ){
        CheckListDetailResponse dtos = checkListService.create(data);
        
        return Response.success("data", dtos);
    }

    /**
     * 룰 수정
     * @param request
     * @param response
     * @param data
     * @return
     */
    @PostMapping("/checklist/state")
    public Response<?> modifyCheckList(
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
