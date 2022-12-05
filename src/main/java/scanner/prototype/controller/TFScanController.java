package scanner.prototype.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import scanner.prototype.exception.ScanException;
import scanner.prototype.response.ApiResponse;
import scanner.prototype.response.ScanResponse;
import scanner.prototype.service.ScanService;
import scanner.prototype.service.StorageServiceImpl;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TFScanController {
    
    private final StorageServiceImpl storageService;
    private final ScanService scanService;

    /**
     * 미사용
     * @param request
     * @param response
     * @param file
     * @return
     * @throws IOException
     */
    @GetMapping("/file/{file}")
    public ResponseEntity<?> downloadFile(
        HttpServletRequest request, 
        HttpServletResponse response,
        @PathVariable String file
    ) throws IOException{
        Resource resource = storageService.loadAsResource(file);
        String contentType = null;

        try {
            contentType = request.getServletContext()
                                .getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            
        }

        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }  

    /**
     * 
     * @param request
     * @param response
     * @param file
     * @return
     * @throws ServletException
     * @throws IllegalStateException
     * @throws IOException
     */
    @PostMapping("/file/{provider}")
    public ApiResponse<?> uploadFile(
        HttpServletRequest request, 
        HttpServletResponse response,
        @PathVariable("provider") String provider,
        @RequestPart("file") MultipartFile file
    ) throws ServletException, IllegalStateException, IOException, NullPointerException, NoSuchAlgorithmException, Exception
    {
        try{
            String[] result = {null, null};

            if(!file.isEmpty()) {
                result = storageService.store(file);
                ScanResponse<?> scanResponse = scanService.scanTerraform(result, provider);
                return ApiResponse.success("check", scanResponse);
            }

            return ApiResponse.fail();
        }
        catch(Exception e){
            return ApiResponse.fail();
        }
    }
}
