package scanner.oauth.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import scanner.common.dto.ResponseDto;
import scanner.oauth.service.AuthService;
import scanner.security.dto.Token;

@ApiOperation("OAuth login")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class OAuthController {

	private final AuthService authService;

	@ApiOperation(value = "Redirect to Github Login page.", notes = "Redirect to Github Login page to get an auth code.")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "redirect", paramType = "param", value = "Redirect Url for FE", required = true, dataTypeClass = String.class)})
	@GetMapping("/github")
	public void githubAuthRedirect(HttpServletResponse response, @RequestParam("redirect") String redirect) {
		authService.redirectGithub(response, redirect);
	}

	@ApiOperation(value = "Redirect to Github Login page.", notes = "RRedirect to Github Login page to get an auth code.")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "code", paramType = "param", value = "Authorization Code from github", required = true, dataTypeClass = String.class)})
	@GetMapping("/callback")
	public ResponseDto<Token> githubAuth(@RequestParam("code") String authCode) {
		Token response = authService.getUserAccessToken(authCode);

		return new ResponseDto<>(response);
	}
}
