
package scanner.prototype.controller;

import java.io.IOException;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import scanner.prototype.dto.FileUploadResponseDto;
import scanner.prototype.response.ApiResponse;
import scanner.prototype.service.StorageServiceImpl;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TFScanController {
    private final StorageServiceImpl storageService;

    @GetMapping("/file/{file}")
    public ResponseEntity<?> downloadFile(
        HttpServletRequest request, 
        HttpServletResponse response,
        @PathVariable String file
    ) throws IOException{
        Resource resource = storageService.loadAsResource(file);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }  

    @PostMapping("/file")
    public ApiResponse<FileUploadResponseDto> uploadFile(
        HttpServletRequest request, 
        HttpServletResponse response,
        @RequestPart("file") MultipartFile file
    ) throws ServletException, IllegalStateException, IOException{
        try{
            if( !file.isEmpty() ) {
                String result = storageService.store(file);

                return ApiResponse.success("data", 
                    new FileUploadResponseDto(
                        file.getOriginalFilename(), 
                        result, 
                        file.getSize(), 
                        file.getContentType()
                    )
                );
            }

            return ApiResponse.fail();
        }
        catch(Exception e){
            return ApiResponse.fail();
        }
    }
}
