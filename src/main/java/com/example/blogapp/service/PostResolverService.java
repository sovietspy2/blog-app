package com.example.blogapp.service;

import com.example.blogapp.model.*;
import com.example.blogapp.repository.*;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.BatchLoader;
import org.dataloader.BatchLoaderEnvironment;
import org.dataloader.DataLoader;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostResolverService {


    private final PostRepository postRepository;

    private final BlogRepository blogRepository;

    private final FileUploadRepository fileUploadRepository;

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    public static final String BLOG_DATA_LOADER = "blog-data-loader";

    public static final String FILE_UPLOAD_DATA_LOADER = "file-upload-data-loader";

    public static final String COMMENTS_DATA_LOADER = "comments-data-loader";

    public static final String USER_DATA_LOADER = "user-data-loader";

    public List<Post> getPosts(Integer page, Integer pageSize, DataFetchingEnvironment environment) {

        environment.getDataLoaderRegistry().register(
                PostResolverService.USER_DATA_LOADER,
                DataLoader.newMappedDataLoader((Set<Integer> ids) ->
                        CompletableFuture.supplyAsync(() -> loadUsers(ids))));


        environment.getDataLoaderRegistry().register(
                PostResolverService.BLOG_DATA_LOADER,
                DataLoader.newMappedDataLoader((Set<Integer> keys, BatchLoaderEnvironment env) ->
                        CompletableFuture.supplyAsync(() -> loadBlogs(keys)
                        )));


        environment.getDataLoaderRegistry().register(
                PostResolverService.FILE_UPLOAD_DATA_LOADER,
                DataLoader.newMappedDataLoader((Set<Integer> ids, BatchLoaderEnvironment env) ->
                        CompletableFuture.supplyAsync(() -> loadFileUploadMap(ids)))
        );

        environment.getDataLoaderRegistry().register(
                PostResolverService.COMMENTS_DATA_LOADER,
                DataLoader.newMappedDataLoader((Set<Integer> ids, BatchLoaderEnvironment env) ->
                        CompletableFuture.supplyAsync(() -> loadComments(ids)))
        );

        return postRepository.findAll(PageRequest.of(page, pageSize)).getContent();
    }

    private Map<Integer, User> loadUsers(Set<Integer> postIds) {
        List<User> users = userRepository.findAllByPostIdIn(postIds);

        Map<Integer, User> map = new HashMap<>();

        postIds.forEach(postId -> {
                    Post post = new Post();
                    post.setId(postId);
                    User user = users.stream().filter(i -> i.getPosts().contains(post)).findAny().get();
                    map.put(postId, user);
                }
        );

        return map;
    }


    public Map<Integer, List<Comment>> loadComments(Set<Integer> postIds) {
        List<Comment> comments = commentRepository.findAllByPostIdIn(postIds);

        return postIds.stream().map(key ->
                Pair.of(key, comments.stream()
                        .filter(item -> item.getPost().getId().equals(key))
                        .collect(Collectors.toList())))
                .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));

    }


    public Map<Integer, List<FileUpload>> loadFileUploadMap(Set<Integer> posts) {

        List<FileUpload> fileUploads = fileUploadRepository.findAllByPostIdIn(posts);

        return posts.stream()
                .map(key -> Pair.of(key, fileUploads.stream()
                        .filter(item -> item.getPost().getId().equals(key))
                        .collect(Collectors.toList())))
                .collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
    }


    public Map<Integer, Blog> loadBlogs(Set<Integer> postsIds) {

        log.info("Starting loadBlogs");

        List<Blog> blogs = blogRepository.findAllByPostIdIn(postsIds);

        Map<Integer, Blog> map = new HashMap<>();

        postsIds.stream().forEach(postId -> {

                    Post post = new Post();
                    post.setId(postId);

                    Blog blog = blogs.stream().filter(blog1 -> blog1.getPosts().contains(post)).findAny().get();

                    map.put(postId, blog);
                }
        );

        log.info("Ending loadBlogs");

        return map;

    }

    public Post getPost(Integer postId) {
        return postRepository.findById(postId).orElseGet(Post::new);
    }

    public Post createPost(String title, String text, Integer userId, Integer blogId, DataFetchingEnvironment environment) {

        environment.getDataLoaderRegistry().register(
                PostResolverService.USER_DATA_LOADER,
                DataLoader.newMappedDataLoader((Set<Integer> ids) ->
                        CompletableFuture.supplyAsync(() -> loadUsers(ids))));


        environment.getDataLoaderRegistry().register(
                PostResolverService.BLOG_DATA_LOADER,
                DataLoader.newMappedDataLoader((Set<Integer> keys, BatchLoaderEnvironment env) ->
                        CompletableFuture.supplyAsync(() -> loadBlogs(keys)
                        )));


        environment.getDataLoaderRegistry().register(
                PostResolverService.FILE_UPLOAD_DATA_LOADER,
                DataLoader.newMappedDataLoader((Set<Integer> ids, BatchLoaderEnvironment env) ->
                        CompletableFuture.supplyAsync(() -> loadFileUploadMap(ids)))
        );

        environment.getDataLoaderRegistry().register(
                PostResolverService.COMMENTS_DATA_LOADER,
                DataLoader.newMappedDataLoader((Set<Integer> ids, BatchLoaderEnvironment env) ->
                        CompletableFuture.supplyAsync(() -> loadComments(ids)))
        );

        // TODO optional proper handling
        User user = userRepository.findById(userId).get();
        Blog blog = blogRepository.findById(blogId).get();

        return postRepository.save(Post.builder()
                .title(title)
                .text(text)
                .blog(blog)
                .user(user)
                .build());
    }

}
