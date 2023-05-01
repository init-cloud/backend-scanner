package scanner.security.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import scanner.common.enums.ResponseCode;

@Component("jwtGlobalEntryPoint")
public class JwtGlobalEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		org.springframework.security.core.AuthenticationException authException) throws IOException, ServletException {
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
		} else {
			setResponse(response, ResponseCode.INVALID_TOKEN);
		}
	}

	private void setResponse(HttpServletResponse response, ResponseCode code) throws IOException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType("application/json");

		JSONObject responseJson = new JSONObject();
		JSONObject errorJson = new JSONObject();
		errorJson.put("code", code.getCode());
		errorJson.put("message", code.getMessage());

		responseJson.put("success", false);
		responseJson.put("data", null);
		responseJson.put("error", errorJson);

		response.getWriter().print(responseJson);
	}
}