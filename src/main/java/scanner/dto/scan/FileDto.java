package scanner.dto.scan;


import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileDto {
    private Integer id;
    private String originalName;
    private String uniqName;
    private Integer size;
    private String type;
}
