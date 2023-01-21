package scanner.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import scanner.response.enums.ResponseCode;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class ApiException extends RuntimeException{

    private Throwable ex;
    private final ResponseCode responseCode;
}
