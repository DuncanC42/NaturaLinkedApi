package bzh.duncan.naturalinkedapi.repository;

import bzh.duncan.naturalinkedapi.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
