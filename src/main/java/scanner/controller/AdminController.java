package scanner.controller;


import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scanner.dto.user.UserManagingDto;
import scanner.dto.user.UserRetrieveDto;
import scanner.response.CommonResponse;
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
    public ResponseEntity<CommonResponse<List<UserRetrieveDto>>> retrieveUser(){
        List<UserRetrieveDto> response = userDetailService.retrieve();

        return ResponseEntity.ok()
                .body(new CommonResponse<>(response));
    }

    @ApiOperation(value = "Manage User",
            notes = "Managing User Authority, Role and State.",
            response = ResponseEntity.class)
    @PostMapping("/user")
    public ResponseEntity<CommonResponse<UserManagingDto>> manageUser(@RequestBody UserManagingDto dto){
        UserManagingDto response = userDetailService.update(dto);

        return ResponseEntity.ok()
                .body(new CommonResponse<>(response));
    }


    @ApiOperation(value = "DEPRECATED",
            notes = "DEPRECATED. Use manageUser",
            response = ResponseEntity.class)
    @PostMapping("/role")
    public ResponseEntity<CommonResponse<UserManagingDto>> updateUserRole(@RequestBody UserManagingDto dto){
        UserManagingDto response = userDetailService.updateRole(dto);

        return ResponseEntity.ok()
                .body(new CommonResponse<>(response));
    }

    @ApiOperation(value = "DEPRECATED",
            notes = "DEPRECATED. Use manageUser",
            response = ResponseEntity.class)
    @PostMapping("/authority")
    public ResponseEntity<CommonResponse<UserManagingDto>> updateUserAuthority(@RequestBody UserManagingDto dto){
        UserManagingDto response = userDetailService.updateAuthority(dto);

        return ResponseEntity.ok()
                .body(new CommonResponse<>(response));
    }
}
