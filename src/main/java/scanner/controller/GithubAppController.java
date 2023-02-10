package scanner.controller;


import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import scanner.dto.CommonResponse;
import scanner.security.dto.GithubToken;
import scanner.service.github.GithubAppService;


@ApiOperation("Github access")
@RestController
@RequestMapping("/api/v1/app")
@RequiredArgsConstructor
public class GithubAppController {

    private final GithubAppService githubAppService;

    @ApiOperation(value = "Parse OAuth Token",
            notes = "Parse Token from callback.")
    @GetMapping("/github")
    public ResponseEntity<CommonResponse<GithubToken>> getToken(
            @RequestParam(value = "access_token") String accessToken,
            @RequestParam(value = "refresh_token") String refreshToken,
            @RequestParam(value = "expires_in") Long expiresIn,
            @RequestParam(value = "refresh_token_expires_in") Long refreshTokenExpiresIn,
            @RequestParam(value = "scope") String scope,
            @RequestParam(value = "token_type") String tokenType){

        return ResponseEntity.ok()
                .body(new CommonResponse<>(new GithubToken(accessToken, refreshToken,
                                expiresIn, refreshTokenExpiresIn, scope, tokenType)));
    }
}
