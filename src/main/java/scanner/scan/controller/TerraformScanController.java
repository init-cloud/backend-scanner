package scanner.scan.controller;

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

import scanner.common.dto.CommonResponse;
import scanner.scan.dto.ScanDto;
import scanner.scan.service.ScanService;
import scanner.scan.service.StorageServiceImpl;

import javax.servlet.http.HttpServletRequest;

@ApiOperation("Terraform Scan API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TerraformScanController {

	private final StorageServiceImpl storageService;
	private final ScanService scanService;

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
	public CommonResponse<ScanDto.Response> uploadFile(@PathVariable("provider") String provider,
		@RequestPart("file") MultipartFile file) {

		String[] result = storageService.store(file);
		ScanDto.Response dtos = scanService.scanTerraform(result, provider);

		return new CommonResponse<>(dtos);
	}
}