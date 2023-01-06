package scanner.response.checklist;

import java.util.List;

import lombok.Getter;
import scanner.dto.CheckListDetailDto;


@Getter
public class CheckListDetailResponse {
    List<CheckListDetailDto> docs;

    public CheckListDetailResponse(CheckListDetailDto dto){
        this.docs.add(dto);
    }

    public CheckListDetailResponse(List<CheckListDetailDto> dto){
        this.docs = dto;
    }
}
