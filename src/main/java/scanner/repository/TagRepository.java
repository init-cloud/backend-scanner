package scanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import scanner.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, String> {
    
}
