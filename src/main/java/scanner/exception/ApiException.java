package scanner.exception;

import lombok.Getter;
import scanner.response.enums.ResponseCode;

@Getter
public class ApiException extends RuntimeException{
    private final ResponseCode responseCode;

    public ApiException(ResponseCode code){
        this.responseCode = code;
    }

    public ApiException(Throwable cause, ResponseCode responseCode){
        this.responseCode = responseCode;
    }

    public ApiException(Exception ex, ResponseCode responseCode){
        this.responseCode = responseCode;
    }
}
