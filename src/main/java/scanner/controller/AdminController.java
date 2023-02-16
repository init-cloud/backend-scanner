package scanner.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
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

	@ApiOperation(value = "Retrieve Users",
		notes = "Retrieve User List.",
		response = ResponseEntity.class)
	@GetMapping("/user")
	public ResponseEntity<CommonResponse<List<UserRetrieveDto>>> userListForAdmin() {
		List<UserRetrieveDto> response = userDetailService.getUserList();

		return ResponseEntity.ok()
			.body(new CommonResponse<>(response));
	}

	@ApiOperation(value = "Manage User",
		notes = "Managing User Authority, Role and State.",
		response = ResponseEntity.class)
	@PostMapping("/user")
	public ResponseEntity<CommonResponse<UserManagingDto>> managingUserDetails(@RequestBody UserManagingDto dto) {
		UserManagingDto response = userDetailService.modifyUserDetails(dto);

		return ResponseEntity.ok()
			.body(new CommonResponse<>(response));
	}
}
