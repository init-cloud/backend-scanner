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
import scanner.exception.ApiException;
import scanner.response.CommonResponse;
import scanner.response.enums.ResponseCode;
import scanner.security.dto.Token;
import scanner.service.user.UsernameService;


@ApiOperation("User and IAM API")
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UsernameService userService;

    @ApiOperation(value = "Signin", notes = "Join. return access token")
    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody UserAuthenticationDto dto){
        try{
            Token response = userService.signin(dto);

            return ResponseEntity.ok()
                    .body(new CommonResponse<>(response));
        } catch(Exception e){
            return CommonResponse.toException(new ApiException(ResponseCode.STATUS_4002));
        }
    }

    @ApiOperation(value = "Signup", notes = "Register. return access token")
    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody UserSignupDto dto){
        try{
            Token response = userService.signup(dto);

            return ResponseEntity.ok()
                    .body(new CommonResponse<>(response));
        } catch(Exception e){
            e.printStackTrace();
            return CommonResponse.toException(new ApiException(ResponseCode.STATUS_5100));
        }
    }
}
