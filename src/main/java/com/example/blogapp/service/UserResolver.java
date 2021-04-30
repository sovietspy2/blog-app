package com.example.blogapp.service;

import com.example.blogapp.model.Post;
import com.example.blogapp.model.User;
import com.example.blogapp.repository.UserRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final UserRepository userRepository;

    public User createUser(String name, String email) {
        return userRepository.save(User.builder().name(name).email(email).build());
    }

}
