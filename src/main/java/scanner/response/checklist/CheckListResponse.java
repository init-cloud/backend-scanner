package scanner.response.checklist;


import java.util.List;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import scanner.dto.CheckListSimpleDto;

@Data
@RequiredArgsConstructor
public class CheckListResponse {
    List<CheckListSimpleDto> data;
}