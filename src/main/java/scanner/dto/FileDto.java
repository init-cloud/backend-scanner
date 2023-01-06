package scanner.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileDto {
    private Integer id;
    private String originalname;
    private String uniqname;
    private Integer size;
    private String type;
}
