package scanner.service.github;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import scanner.common.client.GithubFeignClient;
import scanner.common.enums.ResponseCode;
import scanner.dto.github.Git;
import scanner.exception.ApiException;
import scanner.model.user.User;
import scanner.model.user.UserOAuthToken;
import scanner.repository.OAuthTokenRepository;
import scanner.repository.UserRepository;
import scanner.security.dto.GithubToken;
import scanner.security.provider.JwtTokenProvider;

import java.util.List;

import javax.transaction.Transactional;

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
			saved = oAuthTokenRepository.save(
				new UserOAuthToken(requestUser.getOAuthToken(), tokens, requestUser));
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
