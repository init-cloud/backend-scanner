package scanner.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import scanner.response.enums.ResponseCode;

@Getter
@RequiredArgsConstructor
public class ApiException extends RuntimeException{
    private final ResponseCode responseCode;
}
