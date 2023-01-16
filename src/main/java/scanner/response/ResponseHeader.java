package scanner.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@AllArgsConstructor
@Builder
@Getter
public class ResponseHeader {
    private Boolean isSuccess;
    private int code;
    private String message;
}
