package scanner.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import scanner.dto.user.UserAuthenticationDto;
import scanner.dto.user.UserProfileDto;
import scanner.dto.user.UserSignupDto;
import scanner.dto.CommonResponse;
import scanner.security.dto.Token;
import scanner.service.user.UsernameService;

import javax.servlet.http.HttpServletRequest;

@ApiOperation("User and IAM API")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

	private final UsernameService userService;

	@ApiOperation(value = "Signin", notes = "Login. return access token", response = CommonResponse.class)
	@PostMapping("/signin")
	public CommonResponse<Token> login(@RequestBody UserAuthenticationDto dto) {

		Token response = userService.signin(dto);

		return new CommonResponse<>(response);
	}

	@ApiOperation(value = "Signup", notes = "Register. return access token", response = CommonResponse.class)
	@PostMapping("/signup")
	public CommonResponse<Token> register(@RequestBody UserSignupDto dto) {
		Token response = userService.signup(dto);

		return new CommonResponse<>(response);
	}

	@ApiOperation(value = "Retrieve User Profile", notes = "Retrieve personal profile.", response = CommonResponse.class)
	@GetMapping("/profile")
	public CommonResponse<UserProfileDto> userProfileDetails(HttpServletRequest request) {
		UserProfileDto response = userService.getUserProfile(request.getHeader("Authorization"));

		return new CommonResponse<>(response);
	}

	@ApiOperation(value = "Manage User Profile", notes = "Manage personal profile.", response = CommonResponse.class)
	@PostMapping("/profile")
	public CommonResponse<UserProfileDto> managingUserProfileDetails(@RequestBody UserProfileDto dto) {
		UserProfileDto response = userService.manageUserProfile(dto);

		return new CommonResponse<>(response);
	}

	@ApiOperation(value = "Change Password", notes = "Change user Password.", response = CommonResponse.class)
	@PostMapping("/auth")
	public CommonResponse<Boolean> managingUserPassword(@RequestBody UserAuthenticationDto dto) {
		Boolean response = userService.modifyUserPassword(dto);

		return new CommonResponse<>(response);
	}
}