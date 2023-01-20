package scanner.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiOperation;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import scanner.exception.ApiException;
import scanner.response.CommonResponse;
import scanner.response.ScanResponse;
import scanner.response.enums.ResponseCode;
import scanner.service.ScanService;
import scanner.service.StorageServiceImpl;


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
    public ResponseEntity<?> downloadFile(
        HttpServletRequest request,
        @PathVariable String file
    ){
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
    public ResponseEntity<CommonResponse<ScanResponse>> uploadFile(
        @PathVariable("provider") String provider,
        @RequestPart("file") MultipartFile file) {

        String[] result = {null, null};

        if(file.isEmpty())
            throw new ApiException(ResponseCode.STATUS_4005);

        else{
            result = storageService.store(file);
            ScanResponse dtos= scanService.scanTerraform(result, provider);

            return ResponseEntity.ok()
                    .body(new CommonResponse(dtos));
        }
    }
}
