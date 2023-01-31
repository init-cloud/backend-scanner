package scanner.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum Provider {
    AWS("AWS"),
    OPENSTACK("OPENSTACK"),
    NCP("NCP");

    private final String csp;
}