package scanner.common.exception.handler;

import javax.naming.AuthenticationException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;
import scanner.common.dto.CommonResponse;
import scanner.common.exception.ApiAuthException;

@RestControllerAdvice(value = "authenticationExceptionAdvice")
@Slf4j
public class AuthenticationExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = ApiAuthException.class)
	@ResponseBody
	public ResponseEntity<Object> handleJwtException(ApiAuthException e) {
		log.error("handleJwtException throw JwtException : {}", e.getResponseCode().getMessage());
		return CommonResponse.toException(e);
	}

	@ExceptionHandler(value = AuthenticationException.class)
	@ResponseBody
	public ResponseEntity<Object> handleAuthenticateException(AuthenticationException e) {
		log.error("handleAuthenticateException throw AuthenticateException : {}", e.getMessage());
		return CommonResponse.toException(e);
	}
}
