package scanner.prototype.response.checklist;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import scanner.prototype.dto.CheckListDetailDto;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckListDetailResponse {
    List<CheckListDetailDto> docs;

    public CheckListDetailResponse(CheckListDetailDto dto){
        this.docs.add(dto);
    }
}
