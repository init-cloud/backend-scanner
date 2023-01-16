package scanner.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import scanner.exception.ApiException;
import scanner.response.CommonResponse;

@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<?> handle(ApiException exception){
        return CommonResponse.toException(exception);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handle(Exception exception){
        return CommonResponse.toException(exception);
    }
}
