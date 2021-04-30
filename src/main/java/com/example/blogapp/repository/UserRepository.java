package com.example.blogapp.repository;

import com.example.blogapp.model.Comment;
import com.example.blogapp.model.Post;
import com.example.blogapp.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    //@EntityGraph(attributePaths = {"user"})
    Optional<User> findByPostsContains(Post post);

    //Optional<User> findByPosts(Post post);

}
