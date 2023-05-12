package scanner.security.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import scanner.common.enums.ResponseCode;

@Slf4j
@Component("jwtGlobalEntryPoint")
public class JwtGlobalEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		org.springframework.security.core.AuthenticationException authException) throws IOException {
		Integer exception = (Integer)request.getAttribute("exception");

		if (exception == null) {
			setResponse(response, ResponseCode.INVALID_REQUEST);
		} else if (exception.equals(ResponseCode.EMPTY_TOKEN_CLAIMS.getCode())) {
			setResponse(response, ResponseCode.EMPTY_TOKEN_CLAIMS);
		} else if (exception.equals(ResponseCode.INVALID_TOKEN_SIGNATURE.getCode())) {
			setResponse(response, ResponseCode.INVALID_TOKEN_SIGNATURE);
		} else if (exception.equals(ResponseCode.INVALID_TOKEN.getCode())) {
			setResponse(response, ResponseCode.INVALID_TOKEN);
		} else if (exception.equals(ResponseCode.TOKEN_EXPIRED.getCode())) {
			setResponse(response, ResponseCode.TOKEN_EXPIRED);
		} else if (exception.equals(ResponseCode.UNSUPPORTED_TOKEN.getCode())) {
			setResponse(response, ResponseCode.UNSUPPORTED_TOKEN);
		} else if (exception.equals(ResponseCode.INVALID_TOKEN_FORMAT.getCode())) {
			setResponse(response, ResponseCode.INVALID_TOKEN_FORMAT);
		}
	}

	private void setResponse(HttpServletResponse response, ResponseCode code) throws IOException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType("application/json");

		Map<String, Object> errorMap = new HashMap<>();
		errorMap.put("code", code.getCode());
		errorMap.put("message", code.getMessage());

		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("success", false);
		responseMap.put("data", null);
		responseMap.put("error", errorMap);

		JSONObject responseJson = new JSONObject(responseMap);

		response.getWriter().print(responseJson);
	}
}