package scanner.prototype.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {

    private final static int SUCCESS = 200;
    private final static int BAD_REQUEST = 400;
    private final static int NOT_FOUND = 404;
    private final static int FAILED = 500;
    private final static String SUCCESS_MESSAGE = "SUCCESS";
    private final static String NOT_FOUND_MESSAGE = "NOT FOUND";
    private final static String ServerFAILED_MESSAGE = "서버에서 오류가 발생하였습니다.";
    private final static String BadRequest_MESSAGE = "잘못된 요청입니다.";
    private final static String LoginFAILED_MESSAGE = "계정 또는 패스워드가 잘못 입력되었습니다.";
    private final static String INVALID_ACCESS_TOKEN = "Invalid access token.";
    private final static String INVALID_REFRESH_TOKEN = "Invalid refresh token.";
    private final static String NOT_EXPIRED_TOKEN_YET = "Not expired token yet.";

    private final ApiResponseHeader header;
    private final CheckResponse check;
    private final List<ResultResponse> result;

    public static <T> ApiResponse<T> success(String name, T body) {
        List<ResultResponse> results = new ArrayList<ResultResponse>();
        results.add(new ResultResponse(name, name, name, name, name, name, name, name, name));
        
        return new ApiResponse(
            new ApiResponseHeader(SUCCESS, SUCCESS_MESSAGE), (CheckResponse)body, results);
    }

    public static <T> ApiResponse<T> fail(int errorCode) {
        switch(errorCode){
            case 1: /* 로그인 실패 */
                return new ApiResponse(
                    new ApiResponseHeader(BAD_REQUEST, LoginFAILED_MESSAGE), null, null);
            case 2: /* 서버 오류 */
                return new ApiResponse(
                    new ApiResponseHeader(BAD_REQUEST, ServerFAILED_MESSAGE), null, null);
            default: /* 요청 오류 */
                return new ApiResponse(
                    new ApiResponseHeader(BAD_REQUEST, BadRequest_MESSAGE), null, null);
        }
    }

    public static <T> ApiResponse<T> fail() {
        return new ApiResponse(
            new ApiResponseHeader(BAD_REQUEST, BadRequest_MESSAGE), null, null);
    }

    public static <T> ApiResponse<T> invalidAccessToken() {
        return new ApiResponse(
            new ApiResponseHeader(BAD_REQUEST, INVALID_ACCESS_TOKEN), null, null);
    }

    public static <T> ApiResponse<T> invalidRefreshToken() {
        return new ApiResponse(
            new ApiResponseHeader(BAD_REQUEST, INVALID_REFRESH_TOKEN), null, null);
    }

    public static <T> ApiResponse<T> notExpiredTokenYet() {
        return new ApiResponse(
            new ApiResponseHeader(BAD_REQUEST, NOT_EXPIRED_TOKEN_YET), null, null);
    }
}
