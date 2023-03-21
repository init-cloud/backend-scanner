package scanner.scan.controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import scanner.common.client.ApiFeignClient;
import scanner.common.dto.CommonResponse;
import scanner.common.enums.ResponseCode;
import scanner.common.exception.ApiException;
import scanner.scan.dto.ScanDto;
import scanner.scan.service.AsyncScanService;
import scanner.scan.service.ScanService;
import scanner.scan.service.StorageServiceImpl;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ApiOperation("Terraform Scan API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TerraformScanController {

	private final StorageServiceImpl storageService;
	private final ScanService scanService;
	private final AsyncScanService asyncScanService;
	private final ApiFeignClient apiFeignClient;

	@ApiOperation(value = "Download File", notes = "Unused. Deprecated.", response = ResponseEntity.class)
	@GetMapping("/file/{file}")
	public ResponseEntity<Resource> downloadFile(HttpServletRequest request, @PathVariable String file) {
		Resource resource = storageService.loadAsResource(file);
		String contentType = storageService.getContentType(request, resource);

		return ResponseEntity.ok()
			.contentType(MediaType.parseMediaType(contentType))
			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
			.body(resource);
	}

	@ApiOperation(value = "Scan Terraform File", notes = "Uploads .tf or .zip file to scan.", response = CommonResponse.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "file", paramType = "body", value = "TF file by Form Data", required = true, dataTypeClass = MultipartFile.class)})
	@PostMapping("/file/{provider}")
	public CommonResponse<ScanDto.Response> scanTerraform(@PathVariable("provider") String provider,
		@RequestPart("file") MultipartFile file) {
		String[] result = storageService.store(file);
		long beforeTime = System.currentTimeMillis();
		ScanDto.Response dtos = scanService.scanTerraform(result, provider);
		dtos.setParse(apiFeignClient.getVisualization(provider, result[1]));
		saveScanHistory(dtos, result, provider);
		long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기

		log.info("Scan spent - {}", afterTime - beforeTime);
		return new CommonResponse<>(dtos);
	}

	@ApiOperation(value = "[BETA] Async Scan Terraform File", notes = "Uploads .tf or .zip file to scan.", response = CommonResponse.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", paramType = "header", value = "Access Token", required = true, dataTypeClass = String.class),
		@ApiImplicitParam(name = "file", paramType = "body", value = "TF file by Form Data", required = true, dataTypeClass = MultipartFile.class)})
	@PostMapping("/async/file/{provider}")
	public CommonResponse<ScanDto.Response> scanTerraformAsync(@PathVariable("provider") String provider,
		@RequestPart("file") MultipartFile file) throws ExecutionException, InterruptedException {
		String[] result = storageService.store(file);
		long beforeTime = System.currentTimeMillis();
		CompletableFuture<ScanDto.Response> futureResult = asyncScanService.scanAsync(result, provider);
		Object parse = apiFeignClient.getVisualization(provider, result[1]);
		futureResult.thenAccept(dtos -> {
			if (dtos == null) {
				throw new ApiException(ResponseCode.SCAN_ERROR);
			}
			dtos.setParse(parse);
			saveScanHistory(dtos, result, provider);
			long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기

			log.info("Scan Async spent - {}", afterTime - beforeTime);
		});

		return new CommonResponse<>(futureResult.get());
	}

	private void saveScanHistory(ScanDto.Response result, String[] args, String provider) {
		double[] totalCount = scanService.calc(result.getResult());
		scanService.saveScanDetails(result, args, provider, totalCount);
	}
}