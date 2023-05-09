package scanner.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import scanner.auth.entity.UserOAuthToken;

@Repository
public interface OAuthTokenRepository extends JpaRepository<UserOAuthToken, Long> {
	@Override
	Optional<UserOAuthToken> findById(Long aLong);
}