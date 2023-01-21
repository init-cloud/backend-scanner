package scanner.exception;

import lombok.Getter;
import scanner.response.enums.ResponseCode;

@Getter
public class ApiException extends RuntimeException{
    private final ResponseCode responseCode;

    public ApiException(ResponseCode code){
        this.responseCode = code;
    }
}
