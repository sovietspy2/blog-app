package com.example.blogapp.repository;

import com.example.blogapp.model.Comment;
import com.example.blogapp.model.FileUpload;
import com.example.blogapp.model.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CommentRepository extends CrudRepository<Comment,Integer> {

    Optional<Comment> findByPost(Post post);

    public List<Comment> findAllByPost(Post post);

    List<Comment> findManyByPost(Post post);

    List<Comment> findAll();


    @Query("SELECT F FROM Comment F WHERE F.post.id IN  :ids")
    List<Comment> findAllByPostIdIn(@Param("ids") Set<Integer> ids);

}
