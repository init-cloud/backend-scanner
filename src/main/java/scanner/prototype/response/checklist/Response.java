package scanner.prototype.response.checklist;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import scanner.prototype.response.ApiResponseHeader;

@Getter
@RequiredArgsConstructor
public class Response<T> {

    private final static int SUCCESS = 200;
    private final static int BAD_REQUEST = 400;
    private final static String SUCCESS_MESSAGE = "SUCCESS";
    private final static String ServerFAILED_MESSAGE = "서버에서 오류가 발생하였습니다.";
    private final static String BadRequest_MESSAGE = "잘못된 요청입니다.";
    private final static String LoginFAILED_MESSAGE = "계정 또는 패스워드가 잘못 입력되었습니다.";

    private final ApiResponseHeader header;
    private final T data;

    public static <T> Response<T> success(
        String name, 
        T body
    ) {
        return new Response(
            new ApiResponseHeader(SUCCESS, SUCCESS_MESSAGE), body);
    }

    public static <T> Response<T> fail(int errorCode) {
        switch(errorCode){
            case 1: /* 로그인 오류 */
                return new Response(
                    new ApiResponseHeader(BAD_REQUEST, LoginFAILED_MESSAGE), null);
            case 2: /* 서버 오류 */
                return new Response(
                    new ApiResponseHeader(BAD_REQUEST, ServerFAILED_MESSAGE), null);
            default: /* 요청 오류 */
                return new Response(
                    new ApiResponseHeader(BAD_REQUEST, BadRequest_MESSAGE), null);
        }
    }

    public static <T> Response<T> fail() {
        return new Response(
            new ApiResponseHeader(BAD_REQUEST, BadRequest_MESSAGE), null);
    }
}
