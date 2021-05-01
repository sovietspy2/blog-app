package com.example.blogapp.service;

import com.example.blogapp.model.*;
import com.example.blogapp.repository.BlogRepository;
import com.example.blogapp.repository.CommentRepository;
import com.example.blogapp.repository.FileUploadRepository;
import com.example.blogapp.repository.UserRepository;
import graphql.kickstart.tools.GraphQLResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.dataloader.DataLoader;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class PostFieldsResolver implements GraphQLResolver<Post> {

    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    private final FileUploadRepository fileUploadRepository;

    private final BlogRepository blogRepository;

    public User getUser(Post post) {
        return userRepository.findByPostsContains(post).orElseGet(null);
    }

    public List<Comment> getComments(Post post) {
        return commentRepository.findAllByPost(post);
    }

//    public List<FileUpload> getFiles(Post post) {
//        return fileUploadRepository.findAllByPost(post);
//    }

//    public Blog getBlog(Post post) {
//        return blogRepository.findByPostsContains(post).orElseGet(Blog::new);
//    }

    public CompletableFuture<Blog> getBlog(Post post, DataFetchingEnvironment env) {
        DataLoader<Integer, Blog> dataLoader = env.getDataLoader(PostResolver.BLOG_DATA_LOADER);
        return dataLoader.load(post.getBlog().getId());
    }

    public CompletableFuture<List<FileUpload>> getFiles(Post post, DataFetchingEnvironment env) {
        DataLoader<Integer, List<FileUpload>> dataLoader = env.getDataLoader(PostResolver.FILE_UPLOAD_DATA_LOADER);
        return dataLoader.load(post.getId(), post);
    }

}
