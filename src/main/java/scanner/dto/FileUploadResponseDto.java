package scanner.dto;


import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileUploadResponseDto {
    private String originalName;
    private String uniqName;
    private Long size;
    private String type;
}
