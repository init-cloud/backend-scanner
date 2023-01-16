package scanner.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import scanner.dto.user.UserAuthenticationDto;
import scanner.dto.user.UserSignupDto;
import scanner.response.ResponseHeader;
import scanner.security.dto.Token;
import scanner.security.jwt.JwtTokenProvider;
import scanner.service.user.UsernameService;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UsernameService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> login(@RequestBody UserAuthenticationDto dto){
        try{
            Token response = userService.signin(dto);

            return ResponseEntity.ok()
                    .body(response);
        } catch(Exception e){
            return ResponseEntity.badRequest()
                    .body(null);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody UserSignupDto dto){
        try{
            Token response = userService.signup(dto);

            return ResponseEntity.ok()
                    .body(response);
        } catch(Exception e){
            return ResponseEntity.badRequest()
                    .body(null);
        }
    }
}
