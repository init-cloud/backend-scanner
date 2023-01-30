package scanner.dto.checklist;

import java.util.List;

import lombok.Getter;


@Getter
public class CheckListDetailResponse {
    List<CheckListDetail.Detail> docs;

    public CheckListDetailResponse(CheckListDetail.Detail dto){
        this.docs.add(dto);
    }

    public CheckListDetailResponse(List<CheckListDetail.Detail> dto){
        this.docs = dto;
    }
}
