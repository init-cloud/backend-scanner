package scanner.prototype.response.checklist;


import java.util.List;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import scanner.prototype.dto.CheckListSimpleDto;
import scanner.prototype.dto.UserDto;

@Data
@RequiredArgsConstructor
public class CheckListResponse {
    List<CheckListSimpleDto> data;
    UserDto user;
}