package bzh.duncan.naturalinkedapi.service.post;

import bzh.duncan.naturalinkedapi.exception.PostNotFoundException;
import bzh.duncan.naturalinkedapi.model.Post;
import bzh.duncan.naturalinkedapi.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService{

    private final PostRepository postRepository;


    @Override
    public List<Post> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts;
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
    }

    @Override
    public List<Post> getPostsByAuthor(String author) {
        return postRepository.findByAuthor(author);
    }

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Long existingPostId, Post newPost) {

        Post existingPost = this.getPostById(existingPostId);
        existingPost.setAuthor(newPost.getAuthor());
        existingPost.setTitle(newPost.getTitle());
        existingPost.setContent(newPost.getContent());
        return postRepository.save(existingPost);
    }

    @Override
    public void deletePostById(Long id) {
        postRepository.findById(id).ifPresentOrElse(postRepository::delete, () -> {
            throw new PostNotFoundException(id);
        });
    }
}
