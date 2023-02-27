package scanner.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Provider {
	AWS("AWS"), OPENSTACK("OPENSTACK"), NCP("NCP");

	@Getter
	private final String csp;
}