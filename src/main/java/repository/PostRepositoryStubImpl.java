package repository;

import model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Stub
public class PostRepositoryStubImpl implements PostRepository{

    public final List<Post> listOfPosts = new ArrayList<>();
    private long postNumber = 0;

    public List<Post> all() {
        return listOfPosts;
    }

    public Optional<Post> getById(long id) {
        for (Post post : listOfPosts) {
            if (id == post.getId()) {
                return Optional.of(post);
            }
        }
        return Optional.empty();
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            postNumber++;
            post.setId(postNumber);
            listOfPosts.add(post);
            return post;
        }
        for (Post postOfList : listOfPosts) {
            if (postOfList.getId() == post.getId()) {
                postOfList.setContent(post.getContent());
                return post;
            }
        }
        return post;
    }

    public void removeById(long id) {
        listOfPosts.removeIf(post -> id == post.getId());
        long num = 1;
        for (Post post : listOfPosts) {
            post.setId(num);
            num++;
        }
    }
}
