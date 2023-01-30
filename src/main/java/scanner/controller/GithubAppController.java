package scanner.controller;


import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import scanner.response.CommonResponse;
import scanner.service.github.GithubAppService;


@ApiOperation("Github access")
@RestController
@RequestMapping("/api/v1/app")
@RequiredArgsConstructor
public class GithubAppController {

    private final GithubAppService githubAppService;

    @ApiOperation(value = "OAuth for Github")
    @GetMapping("/auth")
    public void authorizeGithub(){
        githubAppService.requestGithubId();
    }

    @ApiOperation(value = "Callback for Github auth",
    notes = "Callback is implicit.")
    @GetMapping("/callback")
    public ResponseEntity<CommonResponse> callback(
            @RequestParam String code,
            @RequestParam String state){

            Object response = githubAppService.requestAfterRedirect(code, state);

        return ResponseEntity.ok()
                .body(new CommonResponse<>(response));
    }
}
