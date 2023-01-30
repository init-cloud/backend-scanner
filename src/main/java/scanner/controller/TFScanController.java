package scanner.controller;


import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import scanner.dto.CommonResponse;
import scanner.dto.scan.ScanDto;
import scanner.service.ScanService;
import scanner.service.StorageServiceImpl;

import javax.servlet.http.HttpServletRequest;


@ApiOperation("Terraform Scan API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TFScanController {

    private final StorageServiceImpl storageService;
    private final ScanService scanService;

    @ApiOperation(value = "Download File",
            notes = "Unused. Deprecated.",
            response = ResponseEntity.class)
    @GetMapping("/file/{file}")
    public ResponseEntity<Resource> downloadFile(
            HttpServletRequest request,
            @PathVariable String file){
        Resource resource = storageService.loadAsResource(file);
        String contentType = storageService.getContentType(request, resource);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @ApiOperation(value = "Scan Terraform File",
            notes = "Uploads .tf or .zip file to scan.",
            response = ResponseEntity.class)
    @PostMapping("/file/{provider}")
    public ResponseEntity<CommonResponse<ScanDto.Response>> uploadFile(
            @PathVariable("provider") String provider,
            @RequestPart("file") MultipartFile file) {

        String[] result = storageService.store(file);
        ScanDto.Response dtos = scanService.scanTerraform(result, provider);

        return ResponseEntity.ok()
                .body(new CommonResponse<>(dtos));
    }
}