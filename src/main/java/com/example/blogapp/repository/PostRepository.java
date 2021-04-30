package com.example.blogapp.repository;

import com.example.blogapp.model.Post;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {

    Page<Post> findAll(@NotNull Pageable pageable);

    Optional<Post> findById(Integer id);

}
