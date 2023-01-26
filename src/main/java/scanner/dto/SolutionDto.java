package scanner.dto;


import lombok.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SolutionDto {
    private String sol;
    private String code;
}
