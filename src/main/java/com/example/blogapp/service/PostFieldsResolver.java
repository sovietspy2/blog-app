package com.example.blogapp.service;

import com.example.blogapp.model.*;
import com.example.blogapp.repository.BlogRepository;
import com.example.blogapp.repository.CommentRepository;
import com.example.blogapp.repository.FileUploadRepository;
import com.example.blogapp.repository.UserRepository;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public List<FileUpload> getFiles(Post post) {
        return fileUploadRepository.findAllByPost(post);
    }

    public Blog getBlog(Post post) {
        return blogRepository.findByPostsContains(post).orElseGet(Blog::new);
    }

}
