package scanner.dto.scan;


import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileDto {
    private String originalName;
    private String uniqName;
    private Long size;
    private String type;
}
