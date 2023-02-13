package scanner.configuration.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "feign", url = "http://initcloud_parser:8000")
public interface ApiFeignClient {
    @GetMapping(value = "/{provider}/{path}")
    Object getVisualization(@PathVariable("provider") String provider,
                            @PathVariable("path") String path);
}
