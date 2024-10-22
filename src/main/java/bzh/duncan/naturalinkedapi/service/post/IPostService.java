package bzh.duncan.naturalinkedapi.service.post;

import bzh.duncan.naturalinkedapi.model.Post;

import java.util.List;

public interface IPostService {
    public List<Post> getAllPosts();
    public Post getPostById(Long id);
    public List<Post> getPostsByAuthor(String author);
    public Post createPost(Post post);
    public Post updatePost(Long exisitingPostId, Post updatedPost);
    public void deletePostById(Long id);
}
