package scanner.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum UserAuthority {

	ADMIN("OP_ADMIN"), RETRIEVE_REPORT("OP_RETRIEVE_REPORT"), RETRIEVE_HISTORY("OP_RETRIEVE_HISTORY"), SCAN_UPLOAD_FILE(
		"OP_SCAN_UPLOAD_FILE"), RETRIEVE_GIT_CODE("OP_RETRIEVE_GIT_CODE"), SCAN_GIT_CODE(
		"OP_SCAN_GIT_CODE"), MODIFY_CHECKLIST("OP_MODIFY_CHECKLIST"), RETRIEVE_CHECKLIST(
		"OP_RETRIEVE_CHECKLIST"), GUEST("OP_GUEST");

	@Getter
	private final String authority;
}
