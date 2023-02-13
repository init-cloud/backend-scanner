package scanner.configuration;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "feign", url="https://api.github.com", configuration = WebConfig.class)
public interface GithubFeignClient {

    @GetMapping(value = "/users/{USER}/repos")
    List<?> getRepositories(@PathVariable("USER") String user);

    @GetMapping(value = "/repos/{USER}/{REPO}/contents/?ref={BRANCH}")
    List<?> getRepositioryDetails(@PathVariable("USER") String user,
                                  @PathVariable("REPO") String repo,
                                  @PathVariable("BRANCH") String branch);
}
