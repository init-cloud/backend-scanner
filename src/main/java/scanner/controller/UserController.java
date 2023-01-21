package scanner.controller;


import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scanner.dto.user.UserAuthenticationDto;
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
            notes = "Join. return access token",
            response = ResponseEntity.class)
    @PostMapping("/signin")
    public ResponseEntity<CommonResponse<Token>> login(@RequestBody UserAuthenticationDto dto){
        Token response = userService.signin(dto);

        return ResponseEntity.ok()
                .body(new CommonResponse<Token>(response));
    }

    @ApiOperation(value = "Signup",
            notes = "Register. return access token",
            response = ResponseEntity.class)
    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<Token>> register(@RequestBody UserSignupDto dto){
        Token response = userService.signup(dto);

        return ResponseEntity.ok()
                .body(new CommonResponse<Token>(response));
    }
}