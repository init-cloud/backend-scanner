package scanner.scan.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import scanner.common.client.ApiFeignClient;
import scanner.common.enums.ResponseCode;
import scanner.common.exception.ApiException;
import scanner.scan.dto.ScanDto;

@Service
@RequiredArgsConstructor
public class AsyncScanService {

	private final ScanService scanService;
	private final ApiFeignClient apiFeignClient;

	@Async
	public CompletableFuture<ScanDto.Response> scanAsync(String[] args, String provider) {
		CompletableFuture<Object> futureVisualization = apiFeignClient.getVisualizationAsync(provider, args[1]);

		CompletableFuture<ScanDto.Response> scanResultFuture = CompletableFuture.supplyAsync(() -> {
			ScanDto.Response scanResult = scanService.scanTerraformFiles(args, provider);

			if (scanResult == null)
				throw new ApiException(ResponseCode.SCAN_ERROR);

			return scanResult;
		});

		return scanResultFuture.thenCombineAsync(futureVisualization, (scanResult, visualization) -> {
			scanResult.addVisualizingResult(visualization);
			scanService.saveScanHistory(scanResult, args, provider);

			return scanResult;
		});
	}
}

