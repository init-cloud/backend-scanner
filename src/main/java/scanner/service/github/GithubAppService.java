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

    public void getRepos(String token){

    }

    public void getFileTrees(String token, String repo, String branch){

    }
}
