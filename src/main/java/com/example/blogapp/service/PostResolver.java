package com.example.blogapp.service;

import com.example.blogapp.model.Blog;
import com.example.blogapp.model.Post;
import com.example.blogapp.model.User;
import com.example.blogapp.repository.BlogRepository;
import com.example.blogapp.repository.UserRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final PostResolverService postResolverService;

    private final UserRepository userRepository;

    private final BlogRepository blogRepository;

    public Post getPost(Integer postId) {
        return postResolverService.getPost(postId);
    }

    public List<Post> getPosts(Integer page, Integer pageSize, DataFetchingEnvironment environment) {
        return postResolverService.getPosts(page, pageSize, environment);
    }

    public Post createPost(String title, String text, Integer userId, Integer blogId, DataFetchingEnvironment env) {
       return postResolverService.createPost(title, text, userId, blogId, env);
    }


}
