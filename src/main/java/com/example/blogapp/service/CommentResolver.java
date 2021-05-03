package com.example.blogapp.service;

import com.example.blogapp.model.Comment;
import com.example.blogapp.model.Post;
import com.example.blogapp.model.User;
import com.example.blogapp.repository.CommentRepository;
import com.example.blogapp.repository.PostRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.dataloader.BatchLoader;
import org.dataloader.BatchLoaderEnvironment;
import org.dataloader.DataLoader;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Component
@RequiredArgsConstructor
public class CommentResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    public static final String POST_DATA_LOADER = "post-data-loader";


    public List<Comment> getComments(DataFetchingEnvironment env) {

        env.getDataLoaderRegistry().register(POST_DATA_LOADER,
               DataLoader.newDataLoader(postBatchLoader));

        return commentRepository.findAll();
    }

    private final BatchLoader<Integer, Post> postBatchLoader = new BatchLoader<>() {
        @Override
        public CompletionStage<List<Post>> load(List<Integer> postIds) {
            return CompletableFuture.supplyAsync(() -> {
                return postRepository.findAllByIdIn(postIds);
            });
        }
    };


    public Comment createComment(String text, User user, Post post) {
        return commentRepository.save(Comment.builder().text(text).user(user).post(post).build());
    }
}
