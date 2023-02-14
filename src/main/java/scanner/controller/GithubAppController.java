package scanner.controller;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import scanner.dto.CommonResponse;
import scanner.security.dto.GithubToken;
import scanner.service.github.GithubAppService;

import java.util.List;


@ApiOperation("Github access")
@RestController
@RequestMapping("/api/v1/app")
@RequiredArgsConstructor
public class GithubAppController {

    private final GithubAppService githubAppService;

    @ApiOperation(value = "Parse OAuth Token",
            notes = "Parse Token from callback.")
    @GetMapping("/token")
    public ResponseEntity<CommonResponse<GithubToken>> apiAccessToken(
            @NonNull @RequestParam(value = "access_token") String accessToken,
            @NonNull @RequestParam(value = "refresh_token") String refreshToken,
            @NonNull @RequestParam(value = "expires_in") Long expiresIn,
            @NonNull @RequestParam(value = "refresh_token_expires_in") Long refreshTokenExpiresIn,
            @NonNull @RequestParam(value = "scope") String scope,
            @NonNull @RequestParam(value = "token_type") String tokenType){

        GithubToken dtos = new GithubToken(accessToken, refreshToken, expiresIn, refreshTokenExpiresIn, scope, tokenType);

        return ResponseEntity.ok()
                .body(new CommonResponse<>(dtos));
    }


    @ApiOperation(value = "Get Repository List",
            notes = "Get Repository List from Github.")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true),
        @ApiImplicitParam(name = "user", value = "Github user or organization", required = true)
    })
    @GetMapping("/repos/{user}")
    public ResponseEntity<CommonResponse<List<?>>> repositoryList(
            @RequestHeader("Authorization") String token,
            @PathVariable("user") String user
    ){
        List<?> dtos = githubAppService.getRepositories(token, user);

        return ResponseEntity.ok()
                .body(new CommonResponse<>(dtos));
    }

    @ApiOperation(value = "Get Repository Details",
            notes = "Get Repository Details from Github.")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true),
        @ApiImplicitParam(name = "user", value = "Github user or organization", required = true),
        @ApiImplicitParam(name = "repo", value = "Github repository", required = true)
    })
    @GetMapping("/repos/{user}/{repo}")
    public ResponseEntity<CommonResponse<?>> repositoryDetails(
            @RequestHeader("Authorization") String token,
            @PathVariable("user") String user,
            @PathVariable("repo") String repo,
            @Nullable @RequestParam("ref") String branch
    ){
        Object dtos = githubAppService.getRepository(token, user, repo, branch);

        return ResponseEntity.ok()
                .body(new CommonResponse<>(dtos));
    }

    @ApiOperation(value = "Get Commit List",
            notes = "Get Commit List from Github repository, branch.")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true),
        @ApiImplicitParam(name = "user", paramType = "path", value = "Github user or organization", required = true),
        @ApiImplicitParam(name = "repo", paramType = "path", value = "Github repository", required = true),
        @ApiImplicitParam(name = "ref", paramType = "query", value = "Branch", required = false)
    })
    @GetMapping("/repos/{user}/{repo}/commits")
    public ResponseEntity<CommonResponse<?>> commitList(
            @RequestHeader("Authorization") String token,
            @PathVariable("user") String user,
            @PathVariable("repo") String repo,
            @Nullable @RequestParam("ref") String branch
    ){
        List<?> dtos = githubAppService.getCommits(token, user, repo, branch);

        return ResponseEntity.ok()
                .body(new CommonResponse<>(dtos));
    }

    @ApiOperation(value = "Get Commit Details",
            notes = "Get Commit Details from Github repository, branch.")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true),
        @ApiImplicitParam(name = "user", paramType = "path", value = "Github user or organization", required = true),
        @ApiImplicitParam(name = "repo", paramType = "path", value = "Github repository", required = true),
        @ApiImplicitParam(name = "hash", paramType = "path", value = "Commit Hash", required = true),
        @ApiImplicitParam(name = "ref", paramType = "query", value = "Branch", required = false)
    })
    @GetMapping("/repos/{user}/{repo}/commits/{hash}")
    public ResponseEntity<CommonResponse<?>> commitDetails(
            @RequestHeader("Authorization") String token,
            @PathVariable("user") String user,
            @PathVariable("repo") String repo,
            @PathVariable("hash") String hash,
            @Nullable @RequestParam("ref") String branch
    ){
        Object dtos = githubAppService.getCommit(token, user, repo, hash, branch);

        return ResponseEntity.ok()
                .body(new CommonResponse<>(dtos));
    }


    @Deprecated(forRemoval = false)
    @ApiOperation(value = "Download Blobs",
            notes = "Download zip files from Github repository, branch.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true),
            @ApiImplicitParam(name = "user", paramType = "path", value = "Github user or organization", required = true),
            @ApiImplicitParam(name = "repo", paramType = "path", value = "Github repository", required = true),
            @ApiImplicitParam(name = "hash", paramType = "path", value = "Commit Hash", required = true),
            @ApiImplicitParam(name = "ref", paramType = "query", value = "Branch", required = false)
    })
    @GetMapping("/repos/{user}/{repo}/git/blobs/{hash}")
    public ResponseEntity<CommonResponse> gitFiles(
            @RequestHeader("Authorization") String token,
            @PathVariable("user") String user,
            @PathVariable("repo") String repo,
            @PathVariable("hash") String hash,
            @Nullable @RequestParam("ref") String branch
    ){
        githubAppService.getBlobsFromGit(token, user, repo, hash, branch);

        return ResponseEntity.ok()
                .body(new CommonResponse<>(null));
    }
}
