package bzh.duncan.naturalinkedapi.service.image;

import bzh.duncan.naturalinkedapi.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface IImageService {
    List<Image> saveImage(List<MultipartFile> files, Long postId);
    void deleteImageById(Long id);
    void updateImage(MultipartFile file, Long postId);
    List<Image> getAllImages();
    Image getImageById(Long id);
}
