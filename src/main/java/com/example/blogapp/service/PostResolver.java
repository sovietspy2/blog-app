package com.example.blogapp.service;

import com.example.blogapp.model.*;
import com.example.blogapp.repository.BlogRepository;
import com.example.blogapp.repository.CommentRepository;
import com.example.blogapp.repository.FileUploadRepository;
import com.example.blogapp.repository.PostRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.dataloader.BatchLoader;
import org.dataloader.BatchLoaderEnvironment;
import org.dataloader.DataLoader;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final PostRepository postRepository;

    private final BlogRepository blogRepository;

    private final FileUploadRepository fileUploadRepository;

    private final CommentRepository commentRepository;

    public static final String BLOG_DATA_LOADER = "blog-data-loader";

    public static final String FILE_UPLOAD_DATA_LOADER = "file-upload-data-loader";

    public static final String COMMENTS_DATA_LOADER = "comments-data-loader";

    public List<Post> getPosts(Integer page, Integer pageSize, DataFetchingEnvironment environment) {

        environment.getDataLoaderRegistry().register(
                PostResolver.BLOG_DATA_LOADER,
                DataLoader.newDataLoader(new BatchLoader<Integer, Blog>() {
                    @Override
                    public CompletionStage<List<Blog>> load(List<Integer> blogIds) {
                        return CompletableFuture.supplyAsync(() -> {
                            return blogRepository.findAllByIdIn(blogIds);
                        });
                    }
                }));


        environment.getDataLoaderRegistry().register(
                PostResolver.FILE_UPLOAD_DATA_LOADER,
                DataLoader.newMappedDataLoader((Set<Integer> ids, BatchLoaderEnvironment env) ->
                        CompletableFuture.supplyAsync(() -> {
                            return loadFileUploadMap((Map) env.getKeyContexts());
                        }))
        );

        environment.getDataLoaderRegistry().register(
                PostResolver.COMMENTS_DATA_LOADER,
                DataLoader.newMappedDataLoader((Set<Integer> ids, BatchLoaderEnvironment env) ->
                        CompletableFuture.supplyAsync(() -> {
                            return loadComments((Map) env.getKeyContexts());
                        }))
        );

        return postRepository.findAll(PageRequest.of(page, pageSize)).getContent();
    }

//    private DataLoader getFiles() {
//        return DataLoader.newMappedDataLoader((Set<Integer> ids, BatchLoaderEnvironment environment) ->
//             CompletableFuture.supplyAsync(() -> {
//            return loadFileUploadMap((Map) environment.getKeyContexts());
//        }));
//    }



    public Map<Integer, List<Comment>> loadComments(Map<Integer, Post> keyContext) {

        List<Integer> keys = new ArrayList<>(keyContext.keySet());

        List<Comment> comments = commentRepository.findAllByPostIdIn(keys);

        Map<Integer, List<Comment>> data = keys.stream().map(key -> {
            Pair<Integer, List<Comment>> pair = Pair.of(key, comments.stream()
                    .filter(item -> item.getPost().getId().equals(key))
                    .collect(Collectors.toList()));
            return pair;
        }).collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
        return data;

    }


    public Map<Integer, List<FileUpload>> loadFileUploadMap(Map<Integer, Post> keyContext) {

        List<Integer> keys = new ArrayList<>(keyContext
                .keySet());

        List<FileUpload> fileUploads = fileUploadRepository.findAllByPostIdIn(keys);

        Map<Integer, List<FileUpload>> data = keys.stream().map(key -> {
            Pair<Integer, List<FileUpload>> pair = Pair.of(key, fileUploads.stream()
                    .filter(item -> item.getPost().getId().equals(key))
                    .collect(Collectors.toList()));
            return pair;
        }).collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
        return data;

    }

    public Post getPost(Integer postId) {
        return postRepository.findById(postId).orElseGet(Post::new);
    }

    public Post createPost(String title, String text, User user, Blog blog) {
        return postRepository.save(Post.builder()
                .title(title)
                .text(text)
                .blog(blog)
                .user(user)
                .build());
    }


}
