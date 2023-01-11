package scanner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import scanner.response.enums.ResponseCode;

@AllArgsConstructor
@Builder
@Getter
public class ExceptionDto {
    private final int code;
    private final String message;

    public ExceptionDto(ResponseCode res){
        this.code = res.getCode();
        this.message = res.getMessage();
    }
}
