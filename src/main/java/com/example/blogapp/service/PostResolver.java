package com.example.blogapp.service;

import com.example.blogapp.model.Blog;
import com.example.blogapp.model.Post;
import com.example.blogapp.model.User;
import com.example.blogapp.repository.PostRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final PostRepository postRepository;

    public List<Post> getPosts(Integer page, Integer pageSize) {
        return postRepository.findAll(PageRequest.of(page, pageSize)).getContent();
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
