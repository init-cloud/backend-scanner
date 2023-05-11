package scanner.oauth.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import scanner.common.dto.ResponseDto;
import scanner.oauth.service.AuthService;
import scanner.security.dto.Token;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class OAuthController {

	private final AuthService authService;

	@GetMapping("/github")
	public void githubAuthRedirect(HttpServletResponse response, @RequestParam("redirectUri") String redirect) {
		authService.redirectGithub(response, redirect);
	}

	@GetMapping("/callback")
	public ResponseDto<Token> githubAuth(@RequestParam("code") String authCode) {
		Token response = authService.getUserAccessToken(authCode);

		return new ResponseDto<>(response);
	}
}
