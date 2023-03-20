package scanner.user.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import scanner.user.dto.UserAuthDto;
import scanner.user.dto.UserDetailsDto;
import scanner.common.dto.CommonResponse;
import scanner.security.dto.Token;
import scanner.user.service.UsernameService;

import javax.servlet.http.HttpServletRequest;

@ApiOperation("User and IAM API")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

	private final UsernameService userService;

	@ApiOperation(value = "Signin", notes = "Login. return access token", response = CommonResponse.class)
	@PostMapping("/signin")
	public CommonResponse<Token> login(@RequestBody UserAuthDto.Authentication dto) {

		Token response = userService.signin(dto);

		return new CommonResponse<>(response);
	}

	@ApiOperation(value = "Signup", notes = "Register. return access token", response = CommonResponse.class)
	@PostMapping("/signup")
	public CommonResponse<Token> register(@RequestBody UserAuthDto.Signup dto) {
		Token response = userService.signup(dto);

		return new CommonResponse<>(response);
	}

	@ApiOperation(value = "Retrieve User Profile", notes = "Retrieve personal profile.", response = CommonResponse.class)
	@GetMapping("/profile")
	public CommonResponse<UserDetailsDto.Profile> userProfileDetails(HttpServletRequest request) {
		UserDetailsDto.Profile response = userService.getUserProfile(request.getHeader("Authorization"));

		return new CommonResponse<>(response);
	}

	@ApiOperation(value = "Manage User Profile", notes = "Manage personal profile.", response = CommonResponse.class)
	@PostMapping("/profile")
	public CommonResponse<UserDetailsDto.Profile> managingUserProfileDetails(@RequestBody UserDetailsDto.Profile dto) {
		UserDetailsDto.Profile response = userService.manageUserProfile(dto);

		return new CommonResponse<>(response);
	}

	@ApiOperation(value = "Change Password", response = CommonResponse.class)
	@PostMapping("/auth")
	public CommonResponse<Boolean> managingUserPassword(@RequestBody UserAuthDto.Authentication dto) {
		Boolean response = userService.modifyUserPassword(dto);

		return new CommonResponse<>(response);
	}
}