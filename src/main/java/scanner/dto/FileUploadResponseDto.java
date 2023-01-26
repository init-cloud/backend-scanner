package scanner.dto;


import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileUploadResponseDto {
    private String originalname;
    private String uniqname;
    private Long size;
    private String type;
}
