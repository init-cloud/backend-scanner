package scanner.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import scanner.dto.user.UserManagingDto;
import scanner.dto.user.UserRetrieveDto;
import scanner.dto.CommonResponse;
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
	public CommonResponse<List<UserRetrieveDto>> userListForAdmin() {
		List<UserRetrieveDto> response = userDetailService.getUserList();

		return new CommonResponse<>(response);
	}

	@ApiOperation(value = "Manage User", notes = "Managing User Authority, Role and State.", response = CommonResponse.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),})
	@PostMapping("/user")
	public CommonResponse<UserManagingDto> managingUserDetails(@RequestBody UserManagingDto dto) {
		UserManagingDto response = userDetailService.modifyUserDetails(dto);

		return new CommonResponse<>(response);
	}
}
