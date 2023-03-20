package scanner.scan.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import scanner.scan.dto.ScanDto;

@Service
@RequiredArgsConstructor
public class AsyncScanService {

	private final ScanService scanService;

	@Async
	public CompletableFuture<ScanDto.Response> scanAsync(String[] args, String provider) {
		ScanDto.Response result = null;
		try {
			Thread.sleep(500);
			result = scanService.scanTerraform(args, provider);

		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
		}
		return CompletableFuture.completedFuture(result);
	}
}
