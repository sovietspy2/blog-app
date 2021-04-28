package com.example.blogapp.repository;

import com.example.blogapp.model.Post;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {

    @EntityGraph(value = "Post.comments", type = EntityGraph.EntityGraphType.FETCH)
    Page<Post> findAll(@NotNull Pageable pageable);

}
