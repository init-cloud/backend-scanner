package scanner.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadResponseDto {
    private String originalname;
    private String uniqname;
    private Long size;
    private String type;
}
