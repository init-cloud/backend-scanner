package scanner.prototype.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import scanner.prototype.model.ScanResult;

@Repository
public interface RedisRepository extends CrudRepository<ScanResult, String> {
    
}