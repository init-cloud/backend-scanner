package scanner.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum Provider {
    AWS("AWS"),
    NCP("NCP");

    private final String csp;
}