package scanner.configuration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "feign", url = "http://initcloud_parser:8000", configuration = WebConfig.class)
public interface ApiFeignClient {
    @GetMapping(value = "/{provider}/{path}")
    List<?> getVisualization(@PathVariable("provider") String provider,
                            @PathVariable("path") String path);
}
