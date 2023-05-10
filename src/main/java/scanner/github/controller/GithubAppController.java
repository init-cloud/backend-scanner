package scanner.github.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import scanner.common.dto.ResponseDto;
import scanner.github.dto.GitDto;
import scanner.github.service.GithubAppService;
import scanner.security.dto.GithubToken;

import java.util.List;
import java.util.stream.Collectors;

@ApiOperation("Github access")
@RestController
@RequestMapping("/api/v1/app")
@RequiredArgsConstructor
public class GithubAppController {

	private final GithubAppService githubAppService;

	@ApiOperation(value = "Parse and Save OAuth Token", notes = "Parse and Save Token from callback.", response = ResponseDto.class)
	@GetMapping("/token")
	public ResponseDto<GithubToken> apiAccessToken(@NonNull @RequestParam(value = "access_token") String accessToken,
		@Nullable @RequestParam(value = "refresh_token") String refreshToken,
		@Nullable @RequestParam(value = "expires_in") Long expiresIn,
		@Nullable @RequestParam(value = "refresh_token_expires_in") Long refreshTokenExpiresIn,
		@Nullable @RequestParam(value = "scope") String scope,
		@Nullable @RequestParam(value = "token_type") String tokenType) {

		GithubToken dtos = new GithubToken(accessToken, refreshToken, expiresIn, refreshTokenExpiresIn, scope,
			tokenType);

		GithubToken response = githubAppService.addToken(dtos);

		return new ResponseDto<>(response);
	}

	@ApiOperation(value = "Get Repository List", notes = "Get Repository List from Github.", response = ResponseDto.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "user", value = "Github user or organization", required = true, dataTypeClass = String.class)})
	@GetMapping("/repos/{user}")
	public ResponseDto<List<GitDto.RepositoryInfo>> repositoryList(@PathVariable("user") String user) {
		List<GitDto.RepositoryInfo> dtos = githubAppService.getRepositories(user);
		List<GitDto.RepositoryInfo> response = dtos.stream()
			.map(GitDto.RepositoryInfo::toApiRepository)
			.collect(Collectors.toList());
		return new ResponseDto<>(response);
	}

	@ApiOperation(value = "Get Repository Details", notes = "Get Repository Details from Github.", response = ResponseDto.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "user", value = "Github user or organization", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "repo", value = "Github repository", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "ref", paramType = "query", value = "Branch", dataTypeClass = String.class)})
	@GetMapping("/repos/{user}/{repo}")
	public ResponseDto<List<GitDto.File>> repositoryDetails(@PathVariable("user") String user,
		@PathVariable("repo") String repo, @Nullable @RequestParam("ref") String branch) {
		List<GitDto.File> dtos = githubAppService.getRepository(user, repo, branch);

		return new ResponseDto<>(dtos);
	}

	@ApiOperation(value = "Get Commit List", notes = "Get Commit List from Github repository, branch.", response = ResponseDto.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "user", paramType = "path", value = "Github user or organization", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "repo", paramType = "path", value = "Github repository", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "ref", paramType = "query", value = "Branch", dataTypeClass = String.class)})
	@GetMapping("/repos/{user}/{repo}/commits")
	public ResponseDto<List<Object>> commitList(@PathVariable("user") String user, @PathVariable("repo") String repo,
		@Nullable @RequestParam("ref") String branch) {
		List<Object> dtos = githubAppService.getCommits(user, repo, branch);

		return new ResponseDto<>(dtos);
	}

	@ApiOperation(value = "Get Commit Details", notes = "Get Commit Details from Github repository, branch.", response = ResponseDto.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "user", paramType = "path", value = "Github user or organization", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "repo", paramType = "path", value = "Github repository", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "hash", paramType = "path", value = "Commit Hash", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "ref", paramType = "query", value = "Branch", dataTypeClass = String.class)})
	@GetMapping("/repos/{user}/{repo}/commits/{hash}")
	public ResponseDto<Object> commitDetails(@PathVariable("user") String user, @PathVariable("repo") String repo,
		@PathVariable("hash") String hash, @Nullable @RequestParam("ref") String branch) {
		Object dtos = githubAppService.getCommit(user, repo, hash, branch);

		return new ResponseDto<>(dtos);
	}

	@Deprecated(forRemoval = false)
	@ApiOperation(value = "Download Blobs", notes = "Download zip files from Github repository, branch.", response = ResponseDto.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "user", paramType = "path", value = "Github user or organization", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "repo", paramType = "path", value = "Github repository", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "hash", paramType = "path", value = "Commit Hash", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "ref", paramType = "query", value = "Branch", dataTypeClass = String.class)})
	@GetMapping("/repos/{user}/{repo}/git/blobs/{hash}")
	public ResponseDto<?> gitFiles(@PathVariable("user") String user, @PathVariable("repo") String repo,
		@PathVariable("hash") String hash, @Nullable @RequestParam("ref") String branch) {
		githubAppService.getBlobsFromGit(user, repo, hash, branch);

		return new ResponseDto<>(null);
	}
}
