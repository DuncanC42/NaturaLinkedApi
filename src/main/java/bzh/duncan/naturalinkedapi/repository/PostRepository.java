package bzh.duncan.naturalinkedapi.repository;

import bzh.duncan.naturalinkedapi.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    public List<Post> findByAuthor(String author);
}
