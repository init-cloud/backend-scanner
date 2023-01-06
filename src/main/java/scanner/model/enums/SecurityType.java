package scanner.model.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum SecurityType {
    GENERAL("GENERAL"), 
    INCIDENT_RESPONSE("INCIDENT RESPONSE"), 
    ACCESS_CONTROL("ACCESS CONTROL"), 
    MEDIA_PROTECTION("MEDIA PROTECTION"), 
    IDENTIFICATION_AND_AUTHENTICATION("IDENTIFICATION AND AUTHENTICATION"), 
    AUDIT_AND_ACCOUNTABILITY("AUDIT AND ACCOUNTABILITY"), 
    CONFIGURATION_MANAGEMENT("CONFIGURATION MANAGEMENT"), 
    SYSTEM_AND_INFORMATION_INTEGRITY("SYSTEM AND INFORMATION INTEGRITY"), 
    SYSTEM_AND_COMMUNICATIONS_PROTECTION("SYSTEM AND COMMUNICATIONS PROTECTION"), 
    CONTINGENCY_PLANNING("CONTINGENCY PLANNING"), 
    PERFORMANCE_IMPROVEMENTS("PERFORMANCE IMPROVEMENTS");


    private final String type;

    public static SecurityType of(String type) {

        return Arrays.stream(SecurityType.values())
                .filter(r -> r.getType().equals(type))
                .findAny()
                .orElse(GENERAL);
    }
}