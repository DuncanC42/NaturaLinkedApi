package bzh.duncan.naturalinkedapi.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(Long id) {

        super("No post found with the id " + id);
    }
}
