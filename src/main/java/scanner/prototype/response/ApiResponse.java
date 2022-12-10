package scanner.prototype.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {

    private final static int SUCCESS = 200;
    private final static int BAD_REQUEST = 400;
    private final static String SUCCESS_MESSAGE = "SUCCESS";
    private final static String SERVER_FAILED_MESSAGE = "서버에서 오류가 발생하였습니다.";
    private final static String BAD_REQUEST_MESSAGE = "잘못된 요청입니다.";

    private final ApiResponseHeader header;
    private final T scan;
    
    public static <T> ApiResponse<T> success(
        String name, 
        T body
    ) {
        return new ApiResponse(
            new ApiResponseHeader(SUCCESS, SUCCESS_MESSAGE), body);
    }

    public static <T> ApiResponse<T> fail(int errorCode) {
        switch(errorCode){
            case 1: /* 로그인 오류 */
                return new ApiResponse(
                    new ApiResponseHeader(BAD_REQUEST, LoginFAILED_MESSAGE), null);
            case 2: /* 서버 오류 */
                return new ApiResponse(
                    new ApiResponseHeader(BAD_REQUEST, ServerFAILED_MESSAGE), null);
            default: /* 요청 오류 */
                return new ApiResponse(
                    new ApiResponseHeader(BAD_REQUEST, BadRequest_MESSAGE), null);
        }
    }

    public static <T> ApiResponse<T> fail() {
        return new ApiResponse(
            new ApiResponseHeader(BAD_REQUEST, BadRequest_MESSAGE), null);
    }

    public static <T> ApiResponse<T> invalidAccessToken() {
        return new ApiResponse(
            new ApiResponseHeader(BAD_REQUEST, INVALID_ACCESS_TOKEN), null);
    }

    public static <T> ApiResponse<T> invalidRefreshToken() {
        return new ApiResponse(
            new ApiResponseHeader(BAD_REQUEST, INVALID_REFRESH_TOKEN), null);
    }

    public static <T> ApiResponse<T> notExpiredTokenYet() {
        return new ApiResponse(
            new ApiResponseHeader(BAD_REQUEST, NOT_EXPIRED_TOKEN_YET), null);
    }
}
