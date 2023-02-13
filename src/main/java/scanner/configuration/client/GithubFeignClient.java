package scanner.configuration.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "feign", url="https://api.github.com")
public interface GithubFeignClient {

    @GetMapping(value = "/users/{USER}/repos")
    List<?> getRepositoryList(@RequestHeader("Authorization") String token,
                              @PathVariable("USER") String user);

    /* Contents in Repository */
    @GetMapping(value = "/repos/{USER}/{REPO}/contents/?ref={BRANCH}")
    List<?> getRepositoryDetails(@RequestHeader("Authorization") String token,
                                 @PathVariable("USER") String user,
                                 @PathVariable("REPO") String repo,
                                 @PathVariable("BRANCH") String branch);

    @GetMapping(value = "/repos/{USER}/{REPO}/commits?ref={BRANCH}")
    List<?> getCommitList(@RequestHeader("Authorization") String token,
                          @PathVariable("USER") String user,
                          @PathVariable("REPO") String repo,
                          @PathVariable("BRANCH") String branch);

    @GetMapping(value = "/repos/{USER}/{REPO}/commits/{HASH}?ref={BRANCH}")
    List<?> getCommitDetails(@RequestHeader("Authorization") String token,
                             @PathVariable("USER") String user,
                             @PathVariable("REPO") String repo,
                             @PathVariable("HASH") String hash,
                             @PathVariable("BRANCH") String branch);

    /* Blobs from Repository */
    @GetMapping(value = "/repos/{USER}/{REPO}/git/blobs/{HASH}?ref={BRANCH}")
    List<?> getFiles(@RequestHeader("Authorization") String token,
                     @PathVariable("USER") String user,
                     @PathVariable("REPO") String repo,
                     @PathVariable("HASH") String hash,
                     @PathVariable("BRANCH") String branch);
}
