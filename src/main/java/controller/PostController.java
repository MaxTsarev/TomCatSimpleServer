package controller;

import com.google.gson.Gson;
import exception.NotFoundException;
import model.Post;
import service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PostController {

    private final ExecutorService executor = Executors.newCachedThreadPool();
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    public void all(HttpServletResponse response) throws IOException, ExecutionException, InterruptedException {
        response.setContentType(APPLICATION_JSON);
        final var data = executor.submit(service::all).get();
        final var gson = new Gson();
        response.getWriter().print(gson.toJson(data));
    }

    public void getById(long id, HttpServletResponse response) throws IOException, ExecutionException, InterruptedException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        final var content = executor.submit(() -> service.getById(id)).get();
        response.getWriter().print(gson.toJson(content));
    }

    public void save(Reader body, HttpServletResponse response) throws IOException, ExecutionException, InterruptedException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        final var post = gson.fromJson(body, Post.class);
        final var data = executor.submit(() -> service.save(post)).get();
        if (data == null) {
            response.getWriter().print(new NotFoundException("Incorrect id input!"));
            return;
        }
        response.getWriter().print(gson.toJson(data));
    }

    public void removeById(long id, HttpServletResponse response) throws IOException, InterruptedException, ExecutionException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        executor.submit(() -> service.removeById(id));
        response.getWriter().print(gson.toJson(service.all()));
    }
}
