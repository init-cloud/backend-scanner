package scanner.prototype.response;


import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ApiResponseHeader {
    private int code;
    private String message;
}
