package scanner.dto;


import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileDto {
    private Integer id;
    private String originalname;
    private String uniqname;
    private Integer size;
    private String type;
}
