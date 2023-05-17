package scanner.common.exception.handler;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import scanner.common.exception.ApiException;
import scanner.common.dto.ResponseDto;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

	@ExceptionHandler(value = ApiException.class)
	public ResponseEntity<Object> handleApi(ApiException exception) {
		exception.printStackTrace();
		log.error("ApiException: {}", exception.getEx().getMessage());
		return ResponseDto.toException(exception);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> handle(Exception exception) {
		exception.printStackTrace();
		log.error("Exception: {}", exception.getMessage());
		return ResponseDto.toException(exception);
	}
}
