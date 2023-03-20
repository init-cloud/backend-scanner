package scanner.common.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import scanner.common.enums.ResponseCode;
import scanner.common.exception.ApiException;

@AllArgsConstructor
@Builder
@Getter
public class CommonResponse<T> {
	private final Boolean success;
	private final T data;
	private final ExceptionDto error;

	public CommonResponse(@Nullable T data) {
		this.success = true;
		this.data = data;
		this.error = null;
	}

	public static ResponseEntity<Object> toException(ApiException e) {
		return ResponseEntity.status(e.getResponseCode().getHttpStatus())
			.body(CommonResponse.builder()
				.success(false)
				.data(null)
				.error(new ExceptionDto(e.getResponseCode()))
				.build());
	}

	public static ResponseEntity<Object> toException(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(CommonResponse.builder()
				.success(false)
				.data(null)
				.error(new ExceptionDto(ResponseCode.SERVER_ERROR))
				.build());
	}
}
