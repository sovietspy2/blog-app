package com.example.blogapp.service;

import com.example.blogapp.model.Comment;
import com.example.blogapp.model.Post;
import com.example.blogapp.model.User;
import com.example.blogapp.repository.CommentRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final CommentRepository commentRepository;

    public Comment findCommentByPost(Post post) {
        return commentRepository.findByPost(post).orElseGet(Comment::new);
    }

    public List<Comment> findCommentsByPost(Post post) {
        return commentRepository.findManyByPost(post);
    }

    public Comment createComment(String text, User user, Post post) {
        return commentRepository.save(Comment.builder().text(text).user(user).post(post).build());
    }
}
