package scanner.model.enums;

import lombok.Getter;


@Getter
public enum Provider {
    AWS("AWS"),
    NCP("NCP");

    private final String provider;

    private Provider(String provider) { 
        this.provider = provider; 
    }
}