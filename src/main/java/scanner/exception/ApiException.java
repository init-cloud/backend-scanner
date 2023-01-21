package scanner.exception;

import lombok.Getter;
import scanner.response.enums.ResponseCode;

@Getter
public class ApiException extends RuntimeException{

    private Throwable ex;
    private final ResponseCode responseCode;

    public ApiException(ResponseCode code){
        this.responseCode = code;
    }

    public ApiException(Throwable e, ResponseCode code){
        this.ex = e;
        this.responseCode = code;
    }
}
