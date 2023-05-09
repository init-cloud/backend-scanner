package scanner.user.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import scanner.user.dto.UserAuthDto;
import scanner.user.dto.UserDetailsDto;
import scanner.common.dto.ResponseDto;
import scanner.security.dto.Token;
import scanner.user.service.UsernameService;

import javax.servlet.http.HttpServletRequest;

import scanner.security.config.Properties;

@ApiOperation("User and IAM API")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

	private final UsernameService userService;

	@ApiOperation(value = "Signin", notes = "Login. return access token", response = ResponseDto.class)
	@PostMapping("/signin")
	public ResponseDto<Token> login(@RequestBody UserAuthDto.Authentication dto) {

		Token response = userService.signin(dto);

		return new ResponseDto<>(response);
	}

	@ApiOperation(value = "Signup", notes = "Register. return access token", response = ResponseDto.class)
	@PostMapping("/signup")
	public ResponseDto<Token> register(@RequestBody UserAuthDto.Signup dto) {
		Token response = userService.signup(dto);

		return new ResponseDto<>(response);
	}

	@ApiOperation(value = "Retrieve User Profile", notes = "Retrieve personal profile.", response = ResponseDto.class)
	@GetMapping("/profile")
	public ResponseDto<UserDetailsDto.Profile> userProfileDetails() {
		UserDetailsDto.Profile response = userService.getUserProfile();

		return new ResponseDto<>(response);
	}

	@ApiOperation(value = "Manage User Profile", notes = "Manage personal profile.", response = ResponseDto.class)
	@PostMapping("/profile")
	public ResponseDto<UserDetailsDto.Profile> managingUserProfileDetails(@RequestBody UserDetailsDto.Profile dto) {
		UserDetailsDto.Profile response = userService.manageUserProfile(dto);

		return new ResponseDto<>(response);
	}

	@ApiOperation(value = "Change Password", notes = "Change user Password.", response = ResponseDto.class)
	@PostMapping("/auth")
	public ResponseDto<Boolean> managingUserPassword(@RequestBody UserAuthDto.Authentication dto) {
		Boolean response = userService.modifyUserPassword(dto);

		return new ResponseDto<>(response);
	}
}