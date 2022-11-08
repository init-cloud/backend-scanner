package scanner.prototype.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import scanner.prototype.model.User;

public interface UserTokenRepository extends JpaRepository<User, String> {
    
}
