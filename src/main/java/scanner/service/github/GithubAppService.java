package scanner.service.github;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import scanner.common.client.GithubFeignClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GithubAppService {

	private final GithubFeignClient githubFeignClient;

	public List<?> getRepositories(@NonNull String token, @NonNull String user) {
		return githubFeignClient.getRepositoryList(token, user);
	}

	public Object getRepository(@NonNull String token, String user, String repo, String branch) {
		return githubFeignClient.getRepositoryDetails(token, user, repo, branch);
	}

	public List<?> getCommits(@NonNull String token, String user, String repo, String branch) {
		return githubFeignClient.getCommitList(token, user, repo, branch);
	}

	public Object getCommit(@NonNull String token, String user, String repo, String hash, String branch) {
		return githubFeignClient.getCommitDetails(token, user, repo, hash, branch);
	}

	public void getBlobsFromGit(@NonNull String token, String user, String repo, String hash, String branch) {
		githubFeignClient.getFiles(token, user, repo, hash, branch);
	}
}
