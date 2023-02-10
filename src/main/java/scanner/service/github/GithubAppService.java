package scanner.service.github;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scanner.common.dto.HttpParam;
import scanner.common.utils.CommonHttpRequest;
import scanner.security.config.Properties;


@Service
@RequiredArgsConstructor
public class GithubAppService {

    private final Properties properties;

    private static String URL_GITHUB_REPOS  = "https://api.github.com/users/{USER}/repos";
    private static String URL_GITHUB_REPO_DETAIL = "https://api.github.com/repos/{USER}/{REPO}/contents/?ref={BRANCH}";

    public void getRepos(String token){
        CommonHttpRequest.requestHttpGet(URL_GITHUB_REPOS,
                null,
                null,
                new HttpParam.Header("Authorization", "token " + token));
    }

    public void getFileTrees(String token, String repo, String branch){
        CommonHttpRequest.requestHttpGet(URL_GITHUB_REPO_DETAIL,
                new HttpParam.Path(repo),
                new HttpParam.Query("ref", branch),
                new HttpParam.Header("Authorization", "token " + token));
    }
}
