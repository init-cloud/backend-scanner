package scanner.response.checklist;


import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

import scanner.dto.CheckListSimpleDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckListResponse {
    List<CheckListSimpleDto> data;
}