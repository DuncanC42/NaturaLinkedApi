package bzh.duncan.naturalinkedapi.service.image;

import bzh.duncan.naturalinkedapi.exception.ImageNotFoundException;
import bzh.duncan.naturalinkedapi.exception.PostNotFoundException;
import bzh.duncan.naturalinkedapi.model.Image;
import bzh.duncan.naturalinkedapi.model.Post;
import bzh.duncan.naturalinkedapi.repository.ImageRepository;
import bzh.duncan.naturalinkedapi.repository.PostRepository;
import bzh.duncan.naturalinkedapi.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{

    private final ImageRepository imageRepository;
    private final PostService postService;

    @Override
    public List<Image> saveImage(List<MultipartFile> files, Long postId) {
        Post postToLink = postService.getPostById(postId);
        List<Image> imagesToSave = new ArrayList<>();

        for (MultipartFile file: files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setPost(postToLink);

                // Sauvegarder l'image sans l'URL
                Image savedImage = imageRepository.save(image);

                // Construire l'URL après avoir obtenu l'ID
                String buildDownloadPath = "/images/image/download/";
                String downloadUrl = buildDownloadPath + savedImage.getImageId();
                savedImage.setDownloadUrl(downloadUrl);

                // Sauvegarder à nouveau l'image avec l'URL
                imageRepository.save(savedImage);
                imagesToSave.add(savedImage);
            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return imagesToSave;
    }


    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository :: delete,
                () -> {
                    throw new ImageNotFoundException("No image with id : " + id);
                });
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileName(file.getOriginalFilename());
            image.setImage( new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ImageNotFoundException("Image not found with id : " + id));
    }


}
