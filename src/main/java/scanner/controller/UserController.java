package scanner.controller;


import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scanner.dto.user.UserAuthenticationDto;
import scanner.dto.user.UserDto;
import scanner.dto.user.UserSignupDto;
import scanner.response.CommonResponse;
import scanner.security.dto.Token;
import scanner.service.user.UsernameService;


@ApiOperation("User and IAM API")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UsernameService userService;

    @ApiOperation(value = "Signin",
            notes = "Login. return access token",
            response = ResponseEntity.class)
    @PostMapping("/signin")
    public ResponseEntity<CommonResponse<Token>> login(@RequestBody UserAuthenticationDto dto){

        Token response = userService.signin(dto);

        return ResponseEntity.ok()
                .body(new CommonResponse<>(response));
    }

    @ApiOperation(value = "Signup",
            notes = "Register. return access token",
            response = ResponseEntity.class)
    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<Token>> register(@RequestBody UserSignupDto dto){
        Token response = userService.signup(dto);

        return ResponseEntity.ok()
                .body(new CommonResponse<>(response));
    }

    @ApiOperation(value = "Update Role",
            notes = "Managing Role Type.",
            response = ResponseEntity.class)
    @PostMapping("/role")
    public ResponseEntity<CommonResponse<Boolean>> updateAuthority(@RequestBody UserDto dto){
        Boolean response = userService.updateRole(dto);

        return ResponseEntity.ok()
                .body(new CommonResponse<>(response));
    }
}