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
import org.dataloader.BatchLoaderEnvironment;
import org.dataloader.DataLoader;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class CommentResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final CommentDataLoader commentDataLoader;

    public static final String POST_DATA_LOADER = "post-data-loader";


    public List<Comment> getComments(DataFetchingEnvironment env) {

        env.getDataLoaderRegistry().register(POST_DATA_LOADER,
                DataLoader.newMappedDataLoader((Set<Integer> Post, BatchLoaderEnvironment environment) ->
                        CompletableFuture.supplyAsync(() -> getDataLoaderData( (Map) environment.getKeyContexts()))

                ));

        return commentRepository.findAll();
    }


    private Map getDataLoaderData(Map<Integer, Comment> input) {
        Map<Integer, Post> map = new HashMap<Integer, Post>();

        input.forEach((key, value) -> {
            Post post = postRepository.findById(key).get();
            map.put(key, post);
        });

        return map;
    }


    public Comment createComment(String text, User user, Post post) {
        return commentRepository.save(Comment.builder().text(text).user(user).post(post).build());
    }
}
