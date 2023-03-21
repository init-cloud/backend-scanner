package scanner.user.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import scanner.user.dto.UserDetailsDto;
import scanner.common.dto.CommonResponse;
import scanner.security.service.CustomUserDetailService;

import java.util.List;

@ApiOperation("Admin for managing.")
@RequestMapping("/api/v1/admin")
@RestController
@RequiredArgsConstructor
public class AdminController {

	private final CustomUserDetailService userDetailService;

	@ApiOperation(value = "Retrieve Users", notes = "Retrieve User List.", response = CommonResponse.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),})
	@GetMapping("/user")
	public CommonResponse<List<UserDetailsDto.Retrieve>> userListForAdmin() {
		List<UserDetailsDto.Retrieve> response = userDetailService.getUserList();

		return new CommonResponse<>(response);
	}

	@ApiOperation(value = "Manage User", notes = "Managing User Authority, Role and State.", response = CommonResponse.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),})
	@PostMapping("/user")
	public CommonResponse<UserDetailsDto.Managing> managingUserDetails(@RequestBody UserDetailsDto.Managing dto) {
		UserDetailsDto.Managing response = userDetailService.modifyUserDetails(dto);

		return new CommonResponse<>(response);
	}
}
