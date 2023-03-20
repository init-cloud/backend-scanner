package scanner.github.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import scanner.common.client.GithubFeignClient;
import scanner.common.enums.ResponseCode;
import scanner.common.exception.ApiException;
import scanner.dto.github.Git;
import scanner.security.dto.GithubToken;
import scanner.security.provider.JwtTokenProvider;
import scanner.user.entity.User;
import scanner.user.entity.UserOAuthToken;
import scanner.user.repository.OAuthTokenRepository;
import scanner.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class GithubAppService {

	private final GithubFeignClient githubFeignClient;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;
	private final OAuthTokenRepository oAuthTokenRepository;

	@Transactional
	public GithubToken addToken(GithubToken tokens) {
		String username = jwtTokenProvider.getUsername();

		User requestUser = userRepository.findByUsername(username)
			.orElseThrow(() -> new ApiException(ResponseCode.INVALID_USER));

		UserOAuthToken saved;
		if (requestUser.getOAuthToken() != null)
			saved = oAuthTokenRepository.save(new UserOAuthToken(requestUser.getOAuthToken(), tokens, requestUser));
		else
			saved = oAuthTokenRepository.save(GithubToken.toEntity(tokens, requestUser));

		if (saved.getAccessToken().equals(tokens.getAccessToken()))
			return tokens;
		else
			throw new ApiException(ResponseCode.SERVER_STORE_ERROR);
	}

	public List<Git.Repository> getRepositories(@NonNull String user) {
		String token = jwtTokenProvider.getToken();
		return githubFeignClient.getRepositoryList(token, user);
	}

	public List<Git.File> getRepository(String user, String repo, String branch) {
		String token = jwtTokenProvider.getToken();
		return githubFeignClient.getRepositoryDetails(token, user, repo, branch);
	}

	public List<Object> getCommits(String user, String repo, String branch) {
		String token = jwtTokenProvider.getToken();
		return githubFeignClient.getCommitList(token, user, repo, branch);
	}

	public Object getCommit(String user, String repo, String hash, String branch) {
		String token = jwtTokenProvider.getToken();
		return githubFeignClient.getCommitDetails(token, user, repo, hash, branch);
	}

	public void getBlobsFromGit(String user, String repo, String hash, String branch) {
		String token = jwtTokenProvider.getToken();
		githubFeignClient.getFiles(token, user, repo, hash, branch);
	}
}
