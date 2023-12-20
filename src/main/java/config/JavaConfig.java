package config;

import controller.PostController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.PostRepositoryStubImpl;
import service.PostService;
@Configuration
public class JavaConfig {
    @Bean
    public PostController postController(PostService service) {
        return new PostController(service);
    }

    @Bean
    public PostService postService(PostRepositoryStubImpl repository) {
        return new PostService(repository);
    }

    @Bean
    public PostRepositoryStubImpl postRepository() {
        return new PostRepositoryStubImpl();
    }
}
