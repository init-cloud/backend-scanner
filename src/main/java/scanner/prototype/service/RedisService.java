package scanner.prototype.service;

import org.springframework.stereotype.Service;

import scanner.prototype.model.ScanResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;


@Service
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public void setRedisImage(ScanResult cache){

        SetOperations<String, Object> value = redisTemplate.opsForSet();
        value.add(cache.getKey(), cache.getCache());
    }
}
