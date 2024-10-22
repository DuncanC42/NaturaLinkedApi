package bzh.duncan.naturalinkedapi.controller;

import bzh.duncan.naturalinkedapi.exception.PostNotFoundException;
import bzh.duncan.naturalinkedapi.model.Post;
import bzh.duncan.naturalinkedapi.response.ApiResponse;
import bzh.duncan.naturalinkedapi.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
//@RequestMapping("${api.prefix}/posts")
@RequestMapping("posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(new ApiResponse("Success", posts));
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<ApiResponse> getPostById(@PathVariable Long postId) {
        try {
            Post post = postService.getPostById(postId);
            return ResponseEntity.ok(new ApiResponse("FOUND", post));
        } catch ( PostNotFoundException e ) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<ApiResponse> getPostsByAuthor(@PathVariable String author) {
        List<Post> posts = postService.getPostsByAuthor(author);
        return ResponseEntity.ok(new ApiResponse("Success", posts));
    }

    @DeleteMapping("/post/delete/{postId}")
    public ResponseEntity<ApiResponse> deletePostById(@PathVariable Long postId) {
        postService.deletePostById(postId);
        return ResponseEntity.ok(new ApiResponse("Post deleted successfully", null));
    }

    @PostMapping("/post/add")
    public ResponseEntity<ApiResponse> addPost(@RequestBody Post post){
        Post newPost = postService.createPost(post);
        return ResponseEntity.ok(new ApiResponse("Post created successfully", newPost));
    }


    @PutMapping("/post/update/{postId}")
    public ResponseEntity<ApiResponse> updatePost(@RequestBody Post updatedPost, @PathVariable(name = "postId") Long postId){
        Post post = postService.updatePost(postId, updatedPost);
        return ResponseEntity.ok(new ApiResponse("Post updated successfully", post));
    }



}
