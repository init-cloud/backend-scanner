package scanner.common.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @deprecated (for remove, not use)
 */
@Deprecated
@Getter
@NoArgsConstructor
public class HttpRequestHeader {

    private String header = "";

    public void addHeader(String header){
        this.header += header;
    }
}
