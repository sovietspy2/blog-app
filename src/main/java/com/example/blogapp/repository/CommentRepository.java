package com.example.blogapp.repository;

import com.example.blogapp.model.Comment;
import com.example.blogapp.model.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends CrudRepository<Comment,Integer> {

    Optional<Comment> findByPost(Post post);

    @EntityGraph(attributePaths = {"comments"})
    List<Comment> findManyByPost(Post post);
}
