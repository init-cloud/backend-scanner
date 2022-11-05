package scanner.prototype.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import scanner.prototype.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /* 사용자 조회 */
    User findByUserId(String user_id);
    Optional<User> getByUserId(String user_id);

    Page<User> findByRoleType(Pageable pageable, String roleType);

    /* 사용자 권한 변경 */
    @Query(value = "UPDATE user u SET u.role_type = ?3 WHERE u.user_seq = ?1 and u.user_id = ?2", nativeQuery = true)
    User updateUserRole(long user_seq, String user_id, String role);
}