package scanner.prototype.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import scanner.prototype.model.ScanResult;
import scanner.prototype.repository.RedisRepository;
import scanner.prototype.response.ScanResponse;

@Service
@RequiredArgsConstructor
public class CacheService {

    @Autowired
    private final RedisRepository redisRepository;

    public ScanResponse<?> getCached(String key)
    throws IOException, NoSuchAlgorithmException {
        try{
            /* Redis Retrieve */
            ScanResult opt = redisRepository.findById(key).get();
            
            return opt.getCache();
        }catch(NullPointerException ne){
            /* Redis Value Return */
            return null;
        }catch(NoSuchElementException ee){
            return null;
        }
        catch(Exception e){
            return null;
        }
    }

    public ScanResult saveCache(String key, ScanResponse<?> scan)
    throws Exception {
        try{
            return redisRepository.save(new ScanResult(key, scan));
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}