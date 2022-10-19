package scanner.prototype.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NetworkResponse {
    String region;
    String zones;
    String server;
    String subnet;
}
