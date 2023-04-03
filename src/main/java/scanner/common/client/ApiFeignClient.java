package scanner.common.client;

import java.util.concurrent.CompletableFuture;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "feignClient", contextId = "apiFeignClient", url = "http://initcloud_parser:8000")
public interface ApiFeignClient {
	@GetMapping(value = "/{provider}/{path}")
	Object getVisualization(@PathVariable("provider") String provider, @PathVariable("path") String path);

	@Async
	@GetMapping(value = "/{provider}/{path}")
	CompletableFuture<Object> getVisualizationAsync(@PathVariable("provider") String provider,
		@PathVariable("path") String path);
}
