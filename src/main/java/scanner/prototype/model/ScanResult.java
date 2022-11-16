package scanner.prototype.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.NoArgsConstructor;
import scanner.prototype.response.ScanResponse;

@NoArgsConstructor
@RedisHash(value = "cached", timeToLive = 3600)
public class ScanResult {
    
    @Id
    private String key;
    private ScanResponse<?> cached;

    public ScanResult(String key, ScanResponse<?> cached){
        this.key = key;
        this.cached = cached;
    }

    public ScanResponse<?> getCache(){
        return this.cached;
    }

    public String getKey(){
        return this.key;
    }
}
