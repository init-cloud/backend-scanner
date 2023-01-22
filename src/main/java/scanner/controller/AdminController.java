package scanner.controller;


import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scanner.dto.user.UserRoleAuthorityDto;
import scanner.response.CommonResponse;
import scanner.security.service.CustomUserDetailService;
import scanner.service.user.UsernameService;

@ApiOperation("Admin for managing.")
@RequestMapping("/api/v1/admin")
@RestController
@RequiredArgsConstructor
public class AdminController {

    private final CustomUserDetailService userDetailService;
    private final UsernameService usernameService;

    @ApiOperation(value = "Update User Role",
            notes = "Managing User Role Type.",
            response = ResponseEntity.class)
    @PostMapping("/role")
    public ResponseEntity<CommonResponse<UserRoleAuthorityDto>> updateUserRole(@RequestBody UserRoleAuthorityDto dto){
        UserRoleAuthorityDto response = userDetailService.updateRole(dto);

        return ResponseEntity.ok()
                .body(new CommonResponse<>(response));
    }

    @ApiOperation(value = "Update User Authority",
            notes = "Managing User Authority Type.",
            response = ResponseEntity.class)
    @PostMapping("/authority")
    public ResponseEntity<CommonResponse<UserRoleAuthorityDto>> updateUserAuthority(@RequestBody UserRoleAuthorityDto dto){
        UserRoleAuthorityDto response = userDetailService.updateAuthority(dto);

        return ResponseEntity.ok()
                .body(new CommonResponse<>(response));
    }
}
