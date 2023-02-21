package scanner.common.enums;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode {

	/**
	 * @Todo STATUS NAME MUST BE CHANGED.
	 */
	/* Invalid Request */
	TOKEN_EXPIRED(4001, HttpStatus.UNAUTHORIZED, "Expired Token."),
	INVALID_TOKEN(4002, HttpStatus.UNAUTHORIZED, "Invalid Token."),
	INVALID_PROVIDER(4003, HttpStatus.BAD_REQUEST, "Invalid Scan Provider."),
	UNSUPPORTED_FORMAT(4004, HttpStatus.BAD_REQUEST, "Invalid File Format."),
	DATA_MISSING(4005, HttpStatus.BAD_REQUEST, "Data missing. or File missing."),
	INVALID_REQUEST(4007, HttpStatus.BAD_REQUEST, "Invalid Request."),
	INVALID_USER(4008, HttpStatus.BAD_REQUEST, "Invalid User."),
	INVALID_PASSWORD_LENGTH(4009, HttpStatus.BAD_REQUEST, "Password too short."),
	INVALID_PASSWORD_FORMAT(4010, HttpStatus.BAD_REQUEST, "Invalid Password Create rule."),
	EXISTED_USER(4011, HttpStatus.BAD_REQUEST, "User exist."),
	INVALID_AUTHORIIY_TYPE(4012, HttpStatus.BAD_REQUEST, "Invalid Authority."),
	INVALID_AUTHORIIY(4013, HttpStatus.UNAUTHORIZED, "Have no Authority."),
	INVALID_JSON_FORMAT(4014, HttpStatus.UNAUTHORIZED, "Error During Parse JSON."),

	/* Server Error. */
	SERVER_BUSY(5001, HttpStatus.INTERNAL_SERVER_ERROR, "Server busy."),
	SCAN_ERROR(5002, HttpStatus.INTERNAL_SERVER_ERROR, "Scan Error."),
	SERVER_STORE_ERROR(5003, HttpStatus.INTERNAL_SERVER_ERROR, "Could not store the file."),
	SERVER_DECOMPRESS_ERROR(5004, HttpStatus.INTERNAL_SERVER_ERROR, "Could not decompress the file."),
	SERVER_CREATE_DIR_ERROR(5005, HttpStatus.INTERNAL_SERVER_ERROR, "Could not create upload directory."),

	SERVER_LOAD_FILE_ERROR(5006, HttpStatus.INTERNAL_SERVER_ERROR, "Could not load file."),
	SERVER_LOAD_VISUALIZE_ERROR(5007, HttpStatus.INTERNAL_SERVER_ERROR, "Could not load Visualize data."),
	SERVER_UPDATE_USER_ERROR(5008, HttpStatus.INTERNAL_SERVER_ERROR, "Could not update User."),
	SERVER_PASSWORD_ERROR(5009, HttpStatus.INTERNAL_SERVER_ERROR, "Could not change password."),
	SERVER_ERROR(5100, HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error.");

	private final int code;
	private final HttpStatus httpStatus;
	private final String message;
}