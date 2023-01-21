package scanner.exception.handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import scanner.exception.ApiException;
import scanner.response.CommonResponse;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Object> handleApi(ApiException exception){
        return CommonResponse.toException(exception);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handle(Exception exception){
        return CommonResponse.toException(exception);
    }
}
