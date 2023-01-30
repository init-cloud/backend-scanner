package scanner.service.github;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import scanner.common.dto.HttpRequestUrlParam;
import scanner.common.utils.CommonHttpRequest;
import scanner.security.config.Properties;


@Service
@RequiredArgsConstructor
public class GithubAppService {

    private final Properties properties;

    private static String URL_GITHUB_ID_GET = "https://github.com/login/oauth/authorize";
    private static String CALLBACK = "http://localhost:9090/api/v1/app/callback";
    private static String URL_REDIRECT_POST = "https://github.com/login/oauth/access_token";
    private static String URL_API_ACCESS_GET = "https://api.github.com/user";

    public void requestGithubId(){
        CommonHttpRequest request = new CommonHttpRequest();
        HttpRequestUrlParam uri = new HttpRequestUrlParam();

        uri.setUrlQuery("client_id=" + properties.getAppClientId());
        uri.setUrlQuery("&redirect_uri=" + CALLBACK);
        uri.setUrlQuery("&state=" + properties.TMP_RANDOM);

        request.HttpGetRequestBuffer(URL_GITHUB_ID_GET, uri);
    }

    public Object requestAfterRedirect(String code, String state){
        Object token = requestGithubAccessToken(code);

        return token;
    }

    private Object requestGithubAccessToken(String code){
        CommonHttpRequest request = new CommonHttpRequest();
        HttpRequestUrlParam uri = new HttpRequestUrlParam();

        uri.setUrlQuery("client_id=" + properties.getAppClientId());
        uri.setUrlQuery("&client_secret=" + properties.getAppClientSecret());
        uri.setUrlQuery("&redirect_uri=" + CALLBACK);
        uri.setUrlQuery("&code=" + code);

        return request.HttpPostRequestBuffer(URL_REDIRECT_POST, uri,  null);
    }
}
