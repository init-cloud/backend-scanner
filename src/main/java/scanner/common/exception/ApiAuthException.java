package scanner.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import scanner.common.enums.ResponseCode;

@Getter
@RequiredArgsConstructor
public class ApiAuthException extends RuntimeException {
	private final ResponseCode responseCode;
}

