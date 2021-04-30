package com.example.blogapp.service;

import com.example.blogapp.model.Comment;
import com.example.blogapp.model.Post;
import com.example.blogapp.repository.CommentRepository;
import com.example.blogapp.repository.PostRepository;
import graphql.kickstart.tools.GraphQLResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.dataloader.DataLoader;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class CommentFieldsResolver implements GraphQLResolver<Comment> {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

//    public Post getPost(Comment comment) {
//        return postRepository.findByComments(comment).orElseGet(null);
//    }


    public CompletableFuture<Post> getPost(Comment comment, DataFetchingEnvironment env) {
        DataLoader<Integer, Post> dataLoader = env.getDataLoader(CommentResolver.POST_DATA_LOADER);
        return dataLoader.load(comment.getPost().getId(), comment);
    }


}
